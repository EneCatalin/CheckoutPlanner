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

## Running with H2 (in-memory)

If you don't have Postgres installed, you can launch the app with an in-memory H2 database:

In IntelliJ:

- Open "Run/Debug Configurations"
- Set "Active Spring Profiles" to `h2`
- Run the app

Then visit: [http://localhost:8080/h2-console](http://localhost:8080/h2-console)

- JDBC URL: `jdbc:h2:mem:checkoutdb`
- Username: `sa`
- Password: *(leave blank)*