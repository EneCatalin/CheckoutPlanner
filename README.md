# CheckoutPlanner

Simple application that allows shift planning

## Assumptions

The following assumptions were made during development, based on the vague or open-ended nature of the specification:

- ✅ **Admins are not required to honor employee shift wishes.** They are free to assign any employees to any shift
  regardless of existing wishes.
- ✅ **Each shift must have exactly 2 employees.** Any fewer or more will result in a validation error.
- ✅ **An employee may only have one shift per day.** Duplicate shift assignments are prevented.
- ✅ **Wishes are optional.** Employees can submit wishes, but the system will function even if none are submitted.
- ✅ **Admins can view all wishes or filter them by employee name.** While not explicitly required, this was added to
  simulate likely admin behavior and demonstrate API flexibility.
- ✅ **Employees are uniquely identified by name in wish queries.** In a real-world scenario, email or UUIDs would be
  preferable.
- ✅ **Seed route is included for quick testing.** It’s available at `/dev/seed` and inserts 3 employees and 2 admins.
- ✅ **No authentication is implemented.** This was excluded to maintain focus on the planning logic.
- ✅ **Swagger is used to document the API.** For a clearer, self-describing interface.

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