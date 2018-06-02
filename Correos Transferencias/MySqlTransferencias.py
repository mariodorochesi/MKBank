import pymysql
import time
from email.MIMEMultipart import MIMEMultipart
from email.MIMEText import MIMEText
from email.MIMEImage import MIMEImage
from PIL import Image
from PIL import ImageFont
from PIL import ImageDraw
import sys
import smtplib

#Tiempo en segundos para hacer un refresh de la base de datos
timeSleep = 10
#Imagen a procesar con PIL
img = Image.open("test.png")
#Color de datos a mostrar
color_datos = (0,0,0)
#Carga de Fuente para el monto Transferido
font_main = ImageFont.truetype("Tahoma.ttf", 22)
#Carga de Fuente para el resto de Datos
font_mini = ImageFont.truetype("Tahoma.ttf", 16)

print "Iniciando controlador de Transferencias ... "

while(True):
    falla = 0
    try:
        #Se genera una conexion con la base de datos
        conn = pymysql.connect(host = "den1.mysql2.gear.host",user="mkbank",password="mkb.-123",db="mkbank")
        a = conn.cursor()
    except:
        print "No ha sido posible generar una conexion"
        falla = 1

    if falla == 0:

        #Consulta SQL que se desea generar
        sql = 'SELECT * from transferenciaspendientes;'
        countrow = a.execute(sql)
        if(countrow > 0): 
            print "Han sido encontradas " + str(countrow) + " nuevas transferencias a procesar"
        else:
            print "No se han encontrado nuevas transferencias"
        #Se procesan todos los resultados de la tabla y se almacenan en una tupla de tuplas
        data = a.fetchall()

        for i in data:
            #Imagen a procesar con PIL
            img = Image.open("test.png")
            #Se dibuja la pantilla de la nueva Transferencia
            draw = ImageDraw.Draw(img)
            
            #Se obtienen los datos de la nueva Transferencia
            nombreOrigen = i[0]
            rutOrigen = i[1]
            tipoCuentaOrigen = i[2]
            numeroCuentaOrigen = i[3]
            nombreDestinatario = i[4]
            rutDestinatario = i[5]
            tipoCuentaDestinatario = i[6]
            numeroCuentaDestinatario = i[7]
            correoDestinatario = i[8]
            numeroTransferencia = i[9]
            montoTransferencia = i[10]
            fechaTransferencia = str(i[11])
            comentarioTransferencia = i[12]

            if(tipoCuentaOrigen == 0):
                cuentaOrigen = "Cuenta Vista"
            elif(tipoCuentaOrigen == 1):
                cuentaOrigen = "Cuenta Corriente"
            else:
                cuentaOrigen = "Cuenta Ahorro"

            if(tipoCuentaDestinatario == 0):
                cuentaDestinatario = "Cuenta Vista"
            elif(tipoCuentaDestinatario == 1):
                cuentaDestinatario = "Cuenta Corriente"
            else:
                cuentaDestinatario = "Cuenta Ahorro"

            print "Procesando la Transferencia " + str(numeroTransferencia) + " por un monto de " + str(montoTransferencia)

            #Se Dibuja el Monto Transferido en la Plantilla
            draw.text((245,7),str(montoTransferencia) + " CLP",(255,255,255),font=font_main)

            #Se Dibuja el Tipo de Cuenta en la Plantilla
            draw.text((265,120),cuentaOrigen,color_datos,font=font_mini)

            #Se Dibuja el Numero de Cuenta en la Plantilla
            draw.text((265,165),numeroCuentaOrigen,color_datos,font=font_mini)

            #Se Dibuja el Numero de Transferencia en la Plantilla
            draw.text((265,210),numeroTransferencia,color_datos,font=font_mini)

            #Se Dibuja la Fecha y Hora de la Transferencia en la Plantilla
            draw.text((265,255),fechaTransferencia,color_datos,font=font_mini)

            #Se Dibuja el Nombre del destinatario de la Transferencia en la Plantilla
            draw.text((265,395),nombreDestinatario,color_datos,font=font_mini)

            #Se Dibuja el Rut del destinatario de la Transferencia en la Plantilla
            draw.text((265,440),rutDestinatario,color_datos,font=font_mini)

            #Se Dibuja el Banco de la cuenta del destinatario de la Transferencia en la Plantilla
            draw.text((265,485),"MKBank",color_datos,font=font_mini)

            #Se Dibuja el Numero de la cuenta del destinatario de la Transferencia en la Plantilla
            draw.text((265,530),numeroCuentaDestinatario,color_datos,font=font_mini)

            #Se Dibuja el Tipo de Cuenta del Destinatario de la Transferencia en la Plantillla
            draw.text((265,575),cuentaDestinatario,color_datos,font=font_mini)

            #Se Dibuja el Mail del Destinarario de la Transferencia en la Plantilla
            draw.text((265,620),correoDestinatario,color_datos,font=font_mini)

            #Se Dibuja el Comentario de la Transferencia
            draw.text((265,665),comentarioTransferencia,color_datos,font=font_mini)
            #Se guarda la imagen
            img.save("test1.png")

            #Se hace un resize de la imagen
            img = Image.open("test1.png")
            basewidth =  480
            wpercent = (basewidth/float(img.size[0]))
            hsize = int((float(img.size[1])*float(wpercent)))
            img = img.resize((basewidth,hsize), Image.ANTIALIAS)
            img.save('test1.png') 

            strFrom = 'mkbbankofficial@gmail.com'
            strTo = correoDestinatario

            # Create the root message and fill in the from, to, and subject headers
            msgRoot = MIMEMultipart('related')
            msgRoot['Subject'] = 'COMPROBANTE TRANSFERENCIA DE N° ' + numeroTransferencia
            msgRoot['From'] = strFrom
            msgRoot['To'] = strTo
            msgRoot.preamble = 'This is a multi-part message in MIME format.'

            # Encapsulate the plain and HTML versions of the message body in an
            # 'alternative' part, so message agents can decide which they want to display.
            msgAlternative = MIMEMultipart('alternative')
            msgRoot.attach(msgAlternative)

            msgText = MIMEText('This is the alternative plain text message.')
            msgAlternative.attach(msgText)

            # We reference the image in the IMG SRC attribute by the ID we give it below
            msgText = MIMEText('<b>Estimado Cliente:</b> Adjuntamos detalles de su transferencia bancaria<br><img src="cid:image1"><br>', 'html')
            msgAlternative.attach(msgText)

            # This example assumes the image is in the current directory
            fp = open('test1.png', 'rb')
            msgImage = MIMEImage(fp.read())
            fp.close()

            # Define the image's ID as referenced above
            msgImage.add_header('Content-ID', '<image1>')
            msgRoot.attach(msgImage)
            fallados = 0

            # Send the email (this example assumes SMTP authentication is required)
            try:
                smtp = smtplib.SMTP('smtp.gmail.com', 587)
                smtp.starttls()
                smtp.login("mkbbankofficial@gmail.com", "banquito123")
            except:
                fallados = 1
            if fallados == 0:
                smtp.sendmail(strFrom, strTo, msgRoot.as_string())
                smtp.quit()
                print "Eliminando la transferencia " + numeroTransferencia + " de la cola Pendientes"
                query = "delete from transferenciaspendientes where numeroTransferencia = '%s'" % numeroTransferencia
                a.execute(query)
                conn.commit()
            else:
                print "Algo fallo conectandose al Servidor de Google"
            
    else:
        print "Algo fallo conectando a la base de GearHost"
    print "En " + str(timeSleep) + " segundos, se realizara una nueva verificacion"
    
    time.sleep(timeSleep)
