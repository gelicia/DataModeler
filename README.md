# DataModeler
*by Kyle Sletten*

## Features
* Use the ConnectionExplorer to visualize your databases, tables, and columns
* Save information to an easily `diff`-able format to track schema change
* Save information to a simple XML format

## Supported Databases
* MySQL (depends on `Connector/J` from [the MySQL website](http://www.mysql.com/downloads/connector/j/))
* MS SQL Server 2005 to present (depends on the sqljdbc4.jar JDBC driver from [Microsoft](http://msdn.microsoft.com/en-us/sqlserver/aa937724))

## Configuration
### Global
Global configuration files are found in the `/config` directory of the program root.
#### adapters.list
This file is a list of `Adapter` classes to be initialized by the `DriverManager`.

    # sample adapters.list
    
    DisplayName path.to.AdapterClass

### User
User configuration files are found in `%USERPROFILE%\AppData\Local\DataModeler` on Windows machines and `$HOME/.DataModeler` on all others.
#### connections.xml
This file is a list of connection elements that can be used to auto-populate the `ConnectionForm` with values.

    <!-- sample connections.xml -->
	<connections>
		<connection adapter="DisplayName" host="localhost" username="root" password="password" port="1234"/>
	</connections>

##Copyright
Copyright &copy; 2012 Kyle Sletten

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.