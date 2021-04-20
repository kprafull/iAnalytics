# https://vincentarelbundock.github.io/Rdatasets/doc/mosaicData/Weather.html
#

CREATE TABLE weather
("id" VARCHAR(50) PRIMARY KEY,
"city" VARCHAR(50),
"date" TIMESTAMP,
"year" integer,
"month" integer,
"day" integer,
"high_temp" integer,
"avg_temp" integer,
"low_temp" integer,
"high_dewpt" integer,
"avg_dewpt" integer,
"low_dewpt" integer,
"high_humidity" integer,
"avg_humidity" integer,
"low_humidity" integer,
"high_hg" float,
"avg_hg" float,
"low_hg" float,
"high_vis" integer,
"avg_vis" integer,
"low_vis" integer,
"high_wind" integer,
"avg_wind" integer,
"low_wind" integer,
"precip" VARCHAR(100),
"events" VARCHAR(100));

COPY weather("id","city","date","year","month","day","high_temp","avg_temp","low_temp","high_dewpt","avg_dewpt","low_dewpt","high_humidity","avg_humidity","low_humidity","high_hg","avg_hg","low_hg","high_vis","avg_vis","low_vis","high_wind","avg_wind","low_wind","precip","events")
FROM '/Users/prafull/apps/iAnalytics/src/main/resources/data/weather.csv' DELIMITER ',' CSV HEADER;