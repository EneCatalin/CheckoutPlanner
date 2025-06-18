# CheckoutPlanner

Simple application that allows shift planning


# Seed route
I have made a seed route:

POST /dev/seed

# Route example payloads
POST /wishes

{
"employeeId": 1,
"date": "2025-06-20",
"shiftType": "EARLY"
}

---

POST /planning

{
"date": "2025-06-20",
"shiftType": "EARLY",
"employeeIds": [1, 2]
}
---

GET /planning?date=YYYY-MM-DD



# Swagger

http://localhost:8080/swagger-ui.html

