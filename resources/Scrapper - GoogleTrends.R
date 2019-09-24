-------------# INSTALACION DE PAQUETES #-------------

install.packages("gtrendsR")
install.packages("reshape2")

-------------# LIBRERIAS A USAR #-------------
require(gtrendsR)
require(reshape2)
require(data.table)

-------------# CODIGO #-------------

# EXTRAYENDO LA INFORMACIÓN COMPLETA DE GOOGLE TRENDS


google.trends = gtrends(c("Ministerio de Salud", "minsalud", "@minsaludco", 
                          "Juan Pablo Uribe Restrepo")
                        ) #se escriben las palabras claves a buscar

# CREACIÓN DE LOS DATAFRAMES

google.trends$interest_over_time$date= substr(google.trends$interest_over_time$date, 1, 10)  


dftime <- data.frame(t(sapply(google.trends$interest_over_time,c)))

dftime #df de cuando se hicieron las busquedas

dfcity <- data.frame(t(sapply(google.trends$interest_by_city,c)))

dfcity #df de donde se hicieron las busquedas


# TRANSPONER LOS DATAFRAMES

t_dftime <- transpose(dftime)

# COLOCAR FILAS Y COLUMNAS EN ORDEN

colnames(t_dftime) <- rownames(dftime)
rownames(t_dftime) <- colnames(dftime)

t_dfcity <- transpose(dfcity)

colnames(t_dfcity) <- rownames(dfcity)
rownames(t_dfcity) <- colnames(dfcity)

# ESCRITURAS DE DATAFRAMES COMO CSV PARA USO

write_as_csv(t_dftime, "t_dftime.csv", prepend_ids = TRUE, na = "",
             fileEncoding = "UTF-8")

write_as_csv(t_dfcity, "t_dfcity.csv", prepend_ids = TRUE, na = "",
             fileEncoding = "UTF-8")
