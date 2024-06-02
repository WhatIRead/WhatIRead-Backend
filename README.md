# WhatIRead Application

## Steps to Setup

**1. Clone the application**

```bash
git clone https://github.com/WhatIRead/WhatIRead-Backend.git
```

## Explore Rest APIs

The app defines following CRUD APIs.

### Auth

| Method | Url              | Decription | Sample Valid Request Body | 
|--------|------------------|------------|---------------------------|
| POST   | /api/auth/signup | Sign up    | [JSON](#signup)           |
| POST   | /api/auth/signin | Log in     | [JSON](#signin)           |

### Users

| Method | Url                                  | Description                                                                           | Sample Valid Request Body |
|--------|--------------------------------------|---------------------------------------------------------------------------------------|---------------------------|
| GET    | /api/users/me                        | Get logged in user profile                                                            |                           |
| GET    | /api/users/{username}/profile        | Get user profile by username                                                          |                           |
| GET    | /api/users/checkUsernameAvailability | Check if username is available to register                                            |                           |
| GET    | /api/users/checkEmailAvailability    | Check if email is available to register                                               |                           |
| POST   | /api/users                           | Add user (Only for admins)                                                            | [JSON](#usercreate)       |
| PUT    | /api/users/{username}                | Update user (If profile belongs to logged in user or logged in user is admin)         | [JSON](#userupdate)       |
| DELETE | /api/users/{username}                | Delete user (For logged in user or admin)                                             |                           |
| PUT    | /api/users/{username}/giveAdmin      | Give admin role to user (only for admins)                                             |                           |
| PUT    | /api/users/{username}/TakeAdmin      | Take admin role from user (only for admins)                                           |                           |
| PUT    | /api/users/setOrUpdateInfo           | Update user profile (If profile belongs to logged in user or logged in user is admin) | [JSON](#userinfoupdate)   |

### Shelves

| Method | Url             | Description                | Sample Valid Request Body |
|--------|-----------------|----------------------------|---------------------------|
| GET    | /api/shelf/{id} | Get logged in user profile |                           |

## End points

## Swagger UI End-point : [swagger](http://localhost/8085/swagger-ui/index.html)
