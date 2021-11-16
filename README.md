# All the code is completed in Intellj IDE, which you can find the cracked version online.

The file structure can be segmented into the following for better understanding:


The source files are contained in the java.nyu.alex folder.

# Under nyu.alex folder:

&ensp; &ensp; **config**: Set up the cross origin and login interceptor

&ensp; &ensp;**controller**: Used to handle all the frontend request from React

  &ensp; &ensp;**dao:** 
  
 &ensp; &ensp;&ensp; &ensp;  **entity**: Direct mapping from database entity to java class. For example, in the database we have table airline_staff, then in java entity, we have AirlineStaff class as its mapping.
 
  &ensp; &ensp;&ensp; &ensp;**mapper:** Used to define the interface method to directly manipulate the database. For example, we have Airline findAirlineByName(String airlineName) method in IAirlineDao interface,when it is called, the corresponding SQL will be executed to query for the Airline data.
  
   &ensp; &ensp;**interceptor:** Login Interceptor Class for Access Control.
   
  &ensp; &ensp;**service**: Used to provide service for the controller. In other words, the method in the controller will call the corresponding method defined under dao.mapper.
  
 &ensp; &ensp; **utils**:
 
  &ensp; &ensp;&ensp; &ensp;  **dataUtils:** Used to display data for pie chart for example.
  
   &ensp; &ensp;&ensp; &ensp; **serviceUtils:** Used to handle login and register information.
    
# Under resources folder:

  &ensp;&ensp; **img**: Won't be needed.
  
  &ensp; &ensp;&ensp;&ensp;**mapper:** All the sql files are written under MyBatis framework.
  
  &ensp; &ensp;&ensp;&ensp;**public:** Won't be needed.
  
  &ensp; &ensp;&ensp;&ensp;**static:** Won't be needed.
  
  &ensp; &ensp;&ensp;&ensp;**templates:** Won't be needed.
  
# Other Ingredients
  
&ensp;&ensp;**application.yml:** Used to config the dataSource connection information and springboot configuration.

&ensp;&ensp;**pom.xml:** All the external libraries using maven control.
