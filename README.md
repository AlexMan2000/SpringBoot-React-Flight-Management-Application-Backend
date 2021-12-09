# Project Descriptions

## Framework

- **Backend**: Springboot 2.5.4 + Mybatis + Mysql database
  - **MyBatis**是一个Java持久化框架，它通过XML描述符或注解把对象与存储过程或SQL语句关联起来，映射成数据库内对应的纪录
  - **SpringBoot**:轻量级的Java 开发框架。框架的主要优势之一就是其分层架构，分层架构允许使用者选择使用哪一个组件, 本项目采用到的是其面向切面编程(**AOP**)和依赖注入(**IOC**)思想达到对象组件重用的目的

- **Frontend**: React + antd
  - React: 是一个JavaScript该库旨在简化可视界面的开发
  - antd: 预定义组件, 可重用组件.


![image-20211207115937456](C:\Users\DELL\AppData\Roaming\Typora\typora-user-images\image-20211207115937456.png)

- **SQL Execution Logic**

​	![](C:\Users\DELL\Desktop\NYU\NYU Class Materials\NYUFall2021Courses\Database\Project\Execution_Logic.jpg)

![image-20211209105811488](C:\Users\DELL\AppData\Roaming\Typora\typora-user-images\image-20211209105811488.png)









## Backend Structure

![image-20211207111444134](C:\Users\DELL\AppData\Roaming\Typora\typora-user-images\image-20211207111444134.png)

### main/java

#### aop

- Used for authentication before any data accessing requests.

- **AuthAspect.java** for checking roles of the logged in users.

  - If `http://localhost:8080/customer/getMyFlights` is requested by a bookingAgent, the backend server won't 

    write back the data and will return a status code of 502 indicating **role unmatched.**

- **LoginAspect.java**  for checking the logging status of any users.

  - If `http://localhost:8080/customer/getMyFlights` is requested by an anonymous user, the backend server won't 

    write back the data and will return a status code of 504 indicating the state of **unlogged in**.

- **Need*.annotation** for notifying the springframework which method in the Controller to be checked on **roles** and **permissions** before execution. (`NeedAgent` means agent-exclusive actions, `NeedAdmin` means Admin airlinestaff- exclusive actions)
- **NeedLogin.annotation** for notifying the springframwork which method in the Controller requires logging in the first place. 





#### config

- Configure the springframework to support **CORS** data transferring between frontend and backend.





#### controller

- Request-Level Interfaces, executing methods provided in Service-Level interfaces.

- Contains all the backend interfaces for accepting frontend requests including GET, POST, DELETE and PUT.

- **CustomerController.java**/**BookingAgentController.java**/**AirlineStaffController.java**
  - Handle the requests exclusive to the customers/ bookingAgent /airlineStaff.
  - All the method properly handles the security issues by using authentication before the data accessing.

- **IndexController.java**
  - Handle requests for anonymous users.
- **LoginController.java**
  - Handle requests of logging. (three types of login supported)
- **RegisterController.java**
  - Handle requests of registering. (three types of login supported)
- **LogoutController.java**
  - Handle requests of logging out. (three types of login supported)
  - Invalidate the current session stored at the server.
- **TestController.java**
  - Test some use cases during the development stage.





#### service

- Service-Level interfaces, excuting method provided in Dao Level Interfaces.





#### dao

- SQL Level Interface, executing the SQL defined in corresponding **xml** files.





##### entity

- Direct mapping from database entity to java class. For example, in the database we have **table airline_staff**, then in java entity, we have **AirlineStaff class** as its mapping.
- This feature saves huge amount of time in writing queries with regards to filter searching, registering, logging in, and data preprocessing in frontend.

​	![image-20211207114739158](C:\Users\DELL\AppData\Roaming\Typora\typora-user-images\image-20211207114739158.png)







##### mapper

- Define the method to be called by Service layer, executing SQL statements/procedures and functions which are prodived in **resources.mapper** folder.

- Used to define the interface method to directly manipulate the database. For example, we have Airline `findAirlineByName(String airlineName)` method in **IAirlineDao.interface** ,when it is called, the corresponding SQL will be executed to query for the Airline data.

​		

#### resources

##### mapper

- xml files for SQLs



##### img, public, static, templates

- unused, since we are using the separation of frontend and backend, where template language won't be used.





### application.yml

- Configuration for JDBC Connection, and Spring MVC configuration.



### test

- Used for testing in production environment

- Actually untouched due to time limiting.





### SQL Execution Logic

![](C:\Users\DELL\Desktop\NYU\NYU Class Materials\NYUFall2021Courses\Database\Project\Execution_Logic.jpg)





### Interfaces&Functionalities

-  All the functionalities are commented above each interface in the controller files.

- See the source code for more information.





## Frontend Structure

![image-20211207120105679](C:\Users\DELL\AppData\Roaming\Typora\typora-user-images\image-20211207120105679.png)



### build

- build environment



### src

#### component

- component contains all the files for showing the functionalities for different types of users.



#### lib

- pre-defined the displaying columns in the Table for different users.



#### page

- We only use Userpage, which will render all the components in the component folder according to user's actions.



#### **homepage.js**

- The starter page for user to enter the system.



#### **index.js**

- The router page for changing different user types.

- `http://localhost:3000/staff`corresponds to the airline Staff page

  







# Contributions

## Ni Jiasheng(jn2294)

- Backend Coding and Design.
- SQL statements definement.
- Frontend Skeleton Coding,  Callback functions declarations and realization.
- Debugging.



## Yang Muqing(ym2127)

- Frontend Logic Design (redirecting, re-rendering,control logics) and callback function realization.
- Data Injection.
- SQL statements refinement and Optimization.
- Debugging.







# Future Work

- Adding **Multi-Threading** aspects into the framework.
- **Optimize the SQLs** to have better performance.
- Rewrite part of the frontend interface so as to deliver **better user experience.**
- Using **Spring Security Framework** to perform a systematic authentication, may require a thorough rewrite on the database tables.
