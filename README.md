# csv-util

### Instructions
To run the program, find the jar named `csv-util-assembly-0.1.jar` in the top level of this project.

Make sure you have Scala and MySQL environment installed in your machine.

Run the command below in Shell. The first parameter indicates how much data you want to generate. The second is the path for the output file. The third one describes the DBUrl comprised of IP and port, DB name, username, and password.   
    
    scala csv-util-assembly-0.1.jar 100 ./output.csv "localhost:3306/csv_test&root&"
    
### Potential performance issues
