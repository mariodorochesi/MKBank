# @Author: Mario Dorochesi Ollino
# @Date: 14-04-2018

import matplotlib.pyplot as plt #Libreria para Graficas
import os
import xlsxwriter

#Settings

archivoClientes = open("archivoprueba.csv","r")
archivoCuentasBancarias = open("archivoCuentasBancarias.csv","r")

#Definicion de Variables

cantidadClientesHombres = 0
cantidadClientesMujeres = 0
cantidadClientesGeneral = 0
cantidadCuentasGeneral = 0
listaCiudades = list() #Lista para almacenar las ciudades no repetidas
listaCantidadClientesCiudades = list() #Lista para almacenar la cantidad de clientes por ciudad
listaDineroCiudades = list() #Lista para almacenar la cantidad de dinero por ciudad
colores = ['ro','bo','go','co','mo','yo','ro','bo','go','co','mo','yo','ro','bo','go','co','mo','yo'] #Lista para almacenar algunos de los colores y puntuacion a usar en el grafico

listaTuplas = list()



#Se lee el archivo y se cargan en las listas los datos
for lineaClientes in archivoClientes:
    lineaClientes = lineaClientes.strip().split(",.,")
    cantidadClientesGeneral = cantidadClientesGeneral + 1
    #Se determina el sexo del cliente
    if(lineaClientes[12] == "Hombre"):
        cantidadClientesHombres += 1
    else:
        cantidadClientesMujeres +=1

    if(lineaClientes[3] not in listaCiudades):
        listaCiudades.append(lineaClientes[3])
        listaCantidadClientesCiudades.append(1)
        listaDineroCiudades.append(0)
    else:
        listaCantidadClientesCiudades[listaCiudades.index(lineaClientes[3])] += 1
    i = 0
    for lineaCuentas in archivoCuentasBancarias:
        i = i + 1
        lineaCuentas = lineaCuentas.strip().split(",.,")
        if(lineaCuentas[0] == lineaClientes[2]):
            listaDineroCiudades[listaCiudades.index(lineaClientes[3])] += int(lineaCuentas[4])                                                                                 
    #Rewind del archivo
    archivoCuentasBancarias.seek(0)

#Cantidad de cuentas totales en el banco
cantidadCuentasGeneral = i

for i in range(len(listaCiudades)):
    listaTuplas.append((listaDineroCiudades[i],listaCiudades[i]))

listaTuplas = sorted(listaTuplas)
        



#Calculo de la mediana
aux = sorted(listaDineroCiudades)
if(len(aux)%2)==0:
    mediana = aux[len(listaDineroCiudades)/2] + aux[(len(listaDineroCiudades)/2)+1]
    mediana /=2000000
else:
    mediana = aux[len(listaDineroCiudades)/2]/1000000

#Se hace un grafo utilizando pyplot
for i in range(len(listaCiudades)):
    plt.plot(i,listaDineroCiudades[i]/1000000,colores[i%len(listaCiudades)],label=listaCiudades[i])

#Se grafica la mediana
plt.axhline(y=mediana,label="Mediana", linewidth = 2)
plt.title("Dinero acumulado en Ciudades en millones de pesos")
plt.ylabel("Millones de Pesos")
plt.xlabel("Ciudades")
plt.legend()
plt.grid(True)
plt.savefig("Resumen/graficoCiudades.png")

#Se crea un archivo xlsx
workbook = xlsxwriter.Workbook("Resumen/reporte.xlsx")
#Se crea una nueva pagina 
worksheet = workbook.add_worksheet()
#Formatos Extra a Utilizar al Escribir en el XLSX
bold = workbook.add_format({'bold' : True})
money = workbook.add_format({'num_format': '$#,##0'})

#Fila Inicial
row = 5
#Columna Inicial
col = 1

#Se setean los tamanos de las columas en el archivo xlsx
worksheet.set_column('B:B',25)
worksheet.set_column('C:C',15)  
worksheet.set_column('D:D',15)
worksheet.set_column('F:F',20)
worksheet.set_column('E:E',25)
worksheet.set_column('G:G',15)
worksheet.set_column('H:H',15)

#Se escriben las ciudades, junto con el dinero y la cantidad de clientes
for i in range(len(listaCiudades)):
    worksheet.write(row,col,listaCiudades[i])
    worksheet.write(row,col+1,listaDineroCiudades[i],money)
    worksheet.write(row,col+2,listaCantidadClientesCiudades[i],bold)
    row+=1

#Se escriben datos    
worksheet.write('B4','CIUDAD',bold)
worksheet.write('C4','DINERO',bold)
worksheet.write('D4','CLIENTES',bold)
worksheet.write_string('B23','TOTAL DINERO',bold)
worksheet.write_number('C23', sum(listaDineroCiudades),money)
worksheet.write_string('B24','TOTAL CLIENTES',bold)
worksheet.write_string('B25','CLIENTES HOMBRES',bold)
worksheet.write_string('B26','CLIENTES MUJER',bold)
worksheet.write_string('B27','CUENTAS BANCARIAS',bold)
worksheet.write_string('E23','CIUDAD MAS ADINERADA',bold)
worksheet.write_string('E24','CIUDAD MENOS ADINERADA',bold)
worksheet.write_string('E25','DIFERENCIA',bold)
worksheet.write_number('F23',aux[len(aux)-1],money)
worksheet.write_number('F24',aux[0],money)
worksheet.write_number('F25',aux[len(aux)-1]-aux[0],money)
worksheet.write_number('C24',cantidadClientesGeneral,bold)
worksheet.write_number('C25',cantidadClientesHombres,bold)
worksheet.write_number('C26',cantidadClientesMujeres,bold)
worksheet.write_number('C27',cantidadCuentasGeneral,bold)
worksheet.insert_image('E1','Resumen/graficoCiudades.png',{'x_scale':0.8 , 'y_scale' : 0.8})

#Se cierra el archivo xlsx
workbook.close()

#Se cierran ambos archivos
archivoClientes.close()
archivoCuentasBancarias.close()
