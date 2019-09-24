-------------# INSTALACION DE PAQUETES #-------------

#install.packages("dplyr")
#install.packages("rvest")
#install.packages("stringr")

-------------# LIBRERIAS A USAR #-------------

library(dplyr) # para la funcion de creación de data frames
library(rvest) # webscraping
library(stringr) # para manejar el texto y hacer limpieza del mismo 

-------------# CODIGO #-------------
  
# EXTRAYENDO LA INFORMACIÓN COMPLETA DE GOOGLE NEWS

google <- read_html("https://news.google.com/search?q=ministerio%20de%20salud&hl=es-419&gl=CO&ceid=CO%3Aes-419")

# EXTRAYENDO LA INFORMACIÓN DE LAS CABACERAS DE LA NOTICIA

headline_all <- google %>% html_nodes("article") %>% html_text("span") %>%
  str_split("(?<=[a-z0-9!?\\.])(?=[A-Z])")

# str_split("(?<=[a-z0-9Ã¡Ã©Ã­Ã³Ãº!?\\.])(?=[A-Z])") # para Google News en Portuguese

headline_all <- sapply(headline_all, function(x) x[1]) # extrayendo solo el primer elemento

headline_all[1:100] # mirando los 100 primeros registros


# EXTRAYENDO LA INFORMACIÓN DEL TIEMPO

time_all <- google %>% html_nodes("div article div div time") %>% html_text()

time_all[1:100] # mirando los 100 primeros registros

# buscando el vector más pequeño 

min <- min(sapply(list(time_all, headline_all), length))

# cortando la información al tamaño más pequeño

time_all <- time_all[1:min]
headline_all <- headline_all[1:min]


# CREANDO EL DATAFRAME DE DATOS COMPLETO

df_news <- data_frame(time_all, headline_all)

df_news

# CREANDO EL DATAFRAME DE DATOS SOLO DE LAS CABECERAS

df_newsc <- data_frame(headline_all)

df_newsc

# CREANDO LOS ARCHIVOS PLANOS PARA EXPORTARLOS Y PODERLOS UTILIZAR

write_as_csv(df_news, "df_newsoc.csv", prepend_ids = TRUE, na = "",
             fileEncoding = "UTF-8")

write_as_csv(df_newsc, "df_newsoccom.csv", prepend_ids = TRUE, na = "",
             fileEncoding = "UTF-8")