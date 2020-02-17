# ETML
POST /etl{
	sourcetype: postgres
            sourceurl: [url]
            Targettype: bigquery
            Targeturl:url
            sql:
            Targettable:
            [preductioncolumn]
}
Return id

GET /etl/:id
Return the status of the etl

POST /predict {
	etlid:
            sql: 
            returnType: json/table
            returnTable:  
}
