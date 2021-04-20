# iAnalytics
Analytics and visualization of data

## Dashboard view
Dashboard view shows all the charts configured with real time data from the backend

![image](https://user-images.githubusercontent.com/698481/115318884-86172580-a133-11eb-8d69-ef70fb486095.png)

## Designer view
Designer view helps the admin to define new charts/reports. Admin can choose the metrics and how to visualize these metrics from this view. After defining the graph, it can be saved. The saved graphs are displayed on the dashboard.

![image](https://user-images.githubusercontent.com/698481/115320154-29693a00-a136-11eb-82da-3cff98e10e3e.png)

## Postgres Data
The following postgres data should be configured to view the above charts.

```
--
-- Data for Name: analytics_metrics; Type: TABLE DATA; Schema: public; Owner: prafull
--

COPY public.analytics_metrics (id, column_name, column_type, database, database_type, name) FROM stdin;
1	city	STRING	POSTGRES	SQL	city
2	high_temp	INT	POSTGRES	SQL	High Temp
3	low_temp	INT	POSTGRES	SQL	Low Temp
4	avg_temp	INT	POSTGRES	SQL	Avg Temp
5	avg_wind	INT	POSTGRES	SQL	Avg Wind
6	high_wind	INT	POSTGRES	SQL	High Wind
7	low_wind	INT	POSTGRES	SQL	Low Wind
\.
```

```
--
-- Data for Name: analytics_report; Type: TABLE DATA; Schema: public; Owner: prafull
--

COPY public.analytics_report (id, data, name) FROM stdin;
1	{"type":["x-axis","line"],"title":"City High Temp","xAxis":"city","seriesColors":[],"metrics":[1,2],"groupBy":[]}	City High Temp
2	{"type":["x-axis","column"],"title":"City High Temp Bars","xAxis":"city","seriesColors":[],"metrics":[1,2],"groupBy":[]}	City High Temp Bars
3	{"type":["x-axis","spline","spline","column"],"title":"City Temp","xAxis":"city","seriesColors":[],"metrics":[1,2,3,4],"groupBy":[]}	City Temp
4	{"type":["x-axis","spline","column","spline"],"title":"City Wind","xAxis":"city","seriesColors":[],"metrics":[1,5,6,7],"groupBy":[]}	City Wind
5	{"type":["x-axis","area"],"title":"City High Wind","xAxis":"city","seriesColors":[],"metrics":[1,6],"groupBy":[]}	City High Wind
6	{"type":["x-axis","bar"],"title":"City Low Wind","xAxis":"city","seriesColors":[],"metrics":[1,7],"groupBy":[]}	City Low Wind
7	{"type":["x-axis","pie"],"title":"City Avg Wind","xAxis":"city","seriesColors":[],"metrics":[1,5],"groupBy":[]}	City Avg Wind
8	{"type":["x-axis","column","spline"],"title":"City Temp vs Wind","xAxis":"city","seriesColors":[],"metrics":[1,2,6],"groupBy":[]}	City Temp vs Wind
\.
```
