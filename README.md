# All the code is completed in Intellj IDE, which you can find the cracked version online.

The file structure can be segmented into the following for better understanding:

The source files are contained in the java.nyu.alex folder.
Under nyu.alex folder:
  config: Set up the cross origin and login interceptor
  controller: Used to handle all the frontend request from React
  dao: 
    entity: Direct mapping from database entity to java class. 
            For example, in the database we have table airline_staff, then in java entity, we have AirlineStaff class as its mapping.
    mapper: Used to define the interface method to directly manipulate the database. For example, we have Airline findAirlineByName(String airlineName) method in IAirlineDao interface,
            when it is called, the corresponding SQL will be executed to query for the Airline data.
    interceptor: Login Interceptor Class for Access Control.
  service: Used to provide service for the controller. In other words, the method in the controller will call the corresponding method defined under dao.mapper.
  utils:
    dataUtils: Used to display data for pie chart for example.
    serviceUtils: Used to handle login and register information.
    
Under resources folder:
  img: Won't be needed.
  mapper: All the sql files are written under MyBatis framework.
  public: Won't be needed.
  static: Won't be needed.
  templates: Won't be needed.
  
application.yml: Used to config the dataSource connection information and springboot configuration.

pom.xml: All the external libraries using maven control.
