# PetProject
My project implements several important tasks, such as: 
1. Creating an order; 
2. Adding new books to the database; 
3. Viewing and sorting orders; 
4. Modifying an existing order.

Technologies: Java 18, Java Web (Servlet, JSP, JSTL), JDBC, PostgreSQL

Database
Book:
id,
name,
author,
year,
genre,
cost

Order:
id,
customer_name,
date_of_order,
sum

Stock:
id,
order_id,
book_id,
count

Create order:

![image](https://user-images.githubusercontent.com/131879715/234712571-c1f919af-9d66-4307-b217-89967ca72b02.png)

Add new book:

![image](https://user-images.githubusercontent.com/131879715/234713188-da580a27-0bd0-4ebd-9316-d9638e6738cb.png)

Order list:

![image](https://user-images.githubusercontent.com/131879715/234712677-6a16600b-a2b9-4864-a8b1-3ca8b2e01ff9.png)

Edit order:

![image](https://user-images.githubusercontent.com/131879715/234712891-b1956e9a-5a05-4de4-a99c-2b92a8f14582.png)
