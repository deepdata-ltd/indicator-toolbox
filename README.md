# Indicator Toolbox

*Searchable catalog of "red flag" indicators made for public procurements.*



## Building

Requirements:

* JDK 8
* Maven 3

To build the application, run the following command in your terminal:

```bash
mvn clean package
```

The output of this command will be JAR file:

```
target/indicator-toolbox-VERSION.jar
```



## Configuring

Place an `application.yml` file besides the JAR file, and define the configuration properties in this way:

```yaml
db: # MySQL connection parameters
  host: localhost:3306
  name: database_name
  user: username
  pass: password

admins: # Site administrators
  activate: true  # true = will activate accounts listed below
  reset:    false # true = will update passwords as listed below
  accounts:
    - nick: admin1
      pass: bcrypted-password
    - nick: admin2
      pass: bcrypted-password

disqus:
  id: mysite # .disqus.com

pagination:
  itemsPerPage: 10 # default

server.port: 8080 # default
```

Make sure you use spaces instead of tabs (`\t`) for indentation in the YAML file.

For the administrator `pass` values you can use any online encrypter utility which uses *BCrypt* algorithm.

The `pagination` and `server.port` parts are optional, the default values are shown above.



## Running

To start the application, run the following command in your terminal:

```
java -jar indicator-toolbox-VERSION.jar
```

The application will start and run until it gets killed.



## Administrating

Browse to `http://localhost:8080/admin` (replace the host/port to match your configuration) and log in using one of the accounts you defined in the configuration file.

After a successful login, the administration manual will appear and guide you further.



## License

Copyright 2018 DeepData Ltd.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.