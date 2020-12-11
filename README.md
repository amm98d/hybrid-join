# Hybrid Join

This repository is a Java Implementation of the Hybrid Join Algorithm for ETL Process in Data Warehousing.

## Usage

Follow the following steps to run this project:

- Download and install the following using the setups inside this link:
  - MySQL Community (version 8)
  - MySQL Workbench
  - JDK 8
  - Java Eclipse
- Clone this repository into a Windows Machine by running ```git clone https://github.com/amm98d/hybrid-join.git``` 
- Create a database 'metro_db' from MySQL Workbench.
- Run the SQL script 'mysql-stuff/Transaction_and_MasterData_Generator.sql' in your MySQL Workbench.
- Run the SQL script 'mysql-stuff/createDW.sql' in your MySQL Workbench.
- Import this Java Project inside your Java Eclipse. For help on how to do this, please follow: https://www.360logica.com/blog/how-to-import-a-java-project-into-eclipse-ide/
- Run the file src/Main.java inside the Eclipse Project.
- The analysis queries can be found inside 'mysql-stuff/analysis queries/'. Run them inside your MySQL Workbench to view results.

Please note that althought this project might run on Unix based systems as well, it has only been tested on Java 8 in Windows 10.

## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

Please make sure to update tests as appropriate.

## License
[MIT](https://choosealicense.com/licenses/mit/)
