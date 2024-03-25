# Store Application API

## Introduction
This document outlines the design and functionality of a RESTful API for a store application. The API supports user registration, authentication, product management, and cart functionalities.

## API Endpoints

### 1. Register New User
- **Method:** "POST"
- **Endpoint:** "/register"
- **Request Body:**
  ```json
  {
    "email": "my@email.com",
    "password": "123"
  }
  ```
- **Response:**
    - `200 OK` - User successfully registered.
    - `409 Conflict` - If the user already exists.
- **Note:** Passwords are stored securely using a hashing algorithm.

### 2. Login
- **Method:** "POST"
- **Endpoint:** "/login"
- **Request Body:**
  ```json
  {
    "email": "my@email.com",
    "password": "123"
  }
  ```
- **Response:**
  ```json
  {
    "sessionId": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6Im15QGVtYWlsLmNvbSIsImlhdCI6MTY0NTM0ODU2MX0.QP20nEwBTzyp1gjKmWf2GHgHNBXYIjv7XQQ5gsZKjDo"
  }
  ```
- **Note:** Implements session-based authentication.

### 3. Get All Products
- **Method:** "GET"
- **Endpoint:** "/products"
- **Response:**
  ```json
  [
    {
      "id": "2411",
      "title": "Nail gun",
      "available": 8,
      "price": "23.95"
    },
    // Additional products
  ]
  ```

### 4. Add Item to Cart
- **Method:** "POST"
- **Endpoint:** "/cart/add"
- **Request Body:**
  ```json
  {
    "id": "363",
    "quantity": "2"
  }
  ```
- **Response:**
    - `200 OK` - Item successfully added to the cart.
    - `404 Not Found` - If the requested quantity is not available.

### 5. Display Cart Content
- **Method:** "GET"
- **Endpoint:** "/cart"
- **Response:**
  ```json
  [
    {
      "ordinal": 1,
      "productName": "Nail gun",
      "quantity": 2
    },
    // Additional items in the cart
  ]
  ```

### 6. Remove Item from Cart
- **Method:** "DELETE"
- **Endpoint:** "/cart/remove/{id}"
- **Response:**
    - `200 OK` - Item successfully removed from the cart.

### 7. Modify Cart Item
- **Method:** "PUT"
- **Endpoint:** "/cart/modify"
- **Request Body:**
  ```json
  {
    "id": 2,
    "quantity": 3
  }
  ```
- **Response:**
    - `200 OK` - Cart item successfully modified.
    - `404 Not Found` - If the requested quantity is not available.

### 8. Checkout
- **Method:** "POST"
- **Endpoint:** "/checkout"
- **Response:**
    - `200 OK` - Successful order confirmation.
    - `400 Bad Request` - If the checkout fails.

## Optional Functionality

### 1. Preventing Intruder Brute Forcing -- we can do

- Implement rate limiting or CAPTCHA verification to prevent brute force attacks on login endpoints.

### 2. Reset Password -- we can do
- Implement a password reset functionality with email verification.

### 3. Cancel Order -- we can do /// transactional DB
- Allow users to cancel orders and return products back to available status.

### 4. Get User's Order List --- booring
- Provide an endpoint to retrieve a user's order history including order ID, date, total, and status.

## Conclusion
This API provides a comprehensive set of endpoints to manage users, products, and carts for a store application. It ensures security, session management, and optional functionalities to enhance the user experience.
