# D.E.S.S.

*Discrete Event Simulation System*
for Industrial Scheduling Plans

# Short Description

This application can be used to analyse and simulate industrial scheduling plans. These are uploaded to the web application via the frontend or over the available REST-API in form of a `.json` file. The backend uses a discrete event simulation logic to simulate the plans and returns a multitude of different metrics in the categories

- General simulation 
- Machines
- Jobs
- Operations

These are displayed in the frontend in the form of graphs, as well as numerical values. The metrics of two different simulations can be compared to better evaluate these. 

# Manual Installation / Setup
## Dependencies/Necessary Installs
- JDK Java 17
- Maven
- MySQL
- Node
- npm
- npm serve (e.g. via `npm install -g serve`)

## 1. Setup the MYSQL Database
1. Setup and start a MySQL server
2. Setup a database (no need to setup any tables, the app does it automatically). default name used in Backend is `db_event_simulation`
3. Setup a MySQL user with rights to access, read and change the created database

## 2. Building the Backend 
1. `cd` into `web-app/DiscreteEventSimulation`
2. Run `./mvnw clean package`

## 3. Building the Frontend
1. Open `web-frontend/.env.production`
2. Configure the environment variable called `REACT_APP_BACKEND_SERVER_URL` to equal the Server/Backend URL (where the backend jar will be running. On local machine it should be `https://localhost:8080`)
3. `cd` into `web-app/web-frontend`
4. Run `npm run build`

## 4. Start everything
1. Start the backend with `java -DMYSQL_HOST={mysql host} -DDB_NAME={database name} -DDB_USER={database user} -DDB_PASSWORD={database password} -jar target/DiscreteEventSimulation-0.0.1-SNAPSHOT.jar` from `web-app/DiscreteEventSimulation/`
  - `MYSQL_HOST` (Optional) is the url to the host running the mysql service. Default is `localhost`
  - `DB_NAME` (Optional) is the name of the database used in mysql. Default is `db_event_simulation`
  - `DB_USER` (Required) is the username of the created mysql user
  - `DB_PASSWORD` (Required) is the password used for the mysql user
2. Start the frontend with `serve -s build` from `web-app/web-frontend/`
3. Path to the application will be printed in the frontend console. (Default should be `https://localhost:3000`)

# Docker Installation / Setup
## Dependencies / Necessary Installs
- Docker
- docker-compose
- npm 

## 1. Setup docker Images
1. Follow [Building the Backend](#2-building-the-backend)
2. Follow [Building the Frontend](#3-building-the-frontend)
3. run `docker build -t gruppe4/sim-rest-logic-v4 .` in `web-app/DiscreteEventSimulation/`
4. run `docker build -t gruppe4/sim-frontend .` in `web-app/web-frontend/`

## 2. Run docker images
1. run `docker-compose up` in `web-app/`

# REST API

## REST API Endpoints

- REST API base URL: `{server-url}/api`

|             Method Name             | Request Method |                     Path                      |                                        Description                                       |                                    Response                                   |
| :---------------------------------: | :------------: | :-------------------------------------------: | :--------------------------------------------------------------------------------------: | :---------------------------------------------------------------------------: |
|             upload plan             |     `POST`     |                    `/plan`                    |                 Upload the JSON file containing the plan to be simulated                 |                    newly created plan object as JSON string                   |
|             Delete plan             |    `DELETE`    |                `/plan/{planId}`               |                        deletes a plan by its ID from the database                        |                                    nothing                                    |
|            get all plans            |     `GET`      |                  ` /plan/all`                 |                           gets all plans saved in the database                           |         returns a list of all the plans in the database in JSON format        |
|       Start a simulation case       |     `GET`      |  `/plan/{planId}/simulate/{numOfSimulations}` | creates a new simulation case with `{num of simulations}` amount of singular simulations |              returns the ID of the newly created simulation case              |
|      get all simulation cases       |     `GET`      |                   `/sim/all`                  |     gets all the simulation cases saved in the database and their respective results     |               returns a list of simulation cases in JSON format               |
|          delete Simulation          |    `DELETE`    |                 `/sim/{simId}`                |            deletes a simulation case and its data by its ID from the database            |   returns status ok message with ID of successfully deleted simulation case   |
|    get a single simulation case     |     `GET`      |                `/sim/{simId}`                 |                             gets a simulation case by its ID                             |                   returns a simulation case in JSON format                    |
| get the status of a simulation case |     `GET`      |             `/sim/{simId}/status`             |                      gets the status of a simulation case by its ID                      | returns a JSON object containing status information about the simulation case |

## Recommended REST API workflow

1. upload a JSON plan
2. start simulation case using the `plan ID` from the upload plan response
3. check the status of the simulation case using the `simulation case ID` from the response of the previous action
4. when status indicated that the simulation case is done then get the single simulation case using the `simulation case ID`

# JSON plan structure

## General Structure
The JSON format of the industrial processing plan used for the simulation contains three lists:
```json
{
    "jobs": [],
    "machines": [],
    "operations": []
}
```
These lists each contain their respective JSON objects.

## 1. JSON job object
This object is placed in the `jobs` list and contains the basic information for a job consisting of multiple operations on multiple machines. The structure is as follows:
```json
{
    "id": "1",
    "release_date": 0,
    "due_date": 80,
    "cost_per_lateness_time": 2
}
```
- The `id` property represents the job ID and is a string.
- The `release_date` property is an integer representing the earliest point in time the job can start.
- The `due_date property` is an integer representing the deadline for the job to be finished.
- The `cost_per_lateness_time` property is an integer representing the monetary cost per unit of lateness for the job.

## 2. JSON machine object
This object is placed inside the `machines` list and contains information and variables for a single machine used in the production. 
```json
{
    "id":"A",
    "cost_per_time": 3,
    "repair_cost_per_time": 10,
    "breakdown_probability": 0.05,
    "mean": 5,
    "standard_deviation": 0.5
}
```
- The `id` property represents the ID of the machine and is a string.
- The `cost_per_time` property is an integer representing the cost of operating the machine per unit of time.
- The `repair_cost_per_time` property is an integer representing the cost of repairing the machine per unit of time.
- The `breakdown_probability` property is a floating-point number representing the probability that the machine will break down in a given unit of time.
- The `mean` property is an integer representing the mean duration of a machine breakdown.
- The `standard_deviation` property is a floating-point number representing the standard deviation of the machine breakdown duration.

## 3. JSON operation object
This object is placed inside the `objects` list and contains information and variables for a single operation of a job.
```json
{
    "id": "op4",
    "machine_id": "B",
    "job_id": "1",
    "machine_pred": "op5",
    "conditional_preds": ["op1"],
    "duration": 20,
    "release_date": 0,
    "duration_variation_probability": 0.1,
    "duration_standard_deviation": 1
}
```
- The `id` property represents the ID of the operation and is a string.
- The `machine_id` property is a string representing the ID of the machine that the operation is assigned to.
- The `job_id` property is a string representing the ID of the job that the operation belongs to.
- The `machine_pred` property is a string representing the ID of the operation that comes before this operation on the same machine and must be completed before this operation can start.
- The `conditional_preds` property is an array of strings representing the IDs of other operations that must be completed before this operation can start.
- The `duration` property is an integer representing the time required to complete the operation.
- The `release_date` property is an integer representing the earliest point in time the operation can start.
- The `duration_variation_probability` property is a floating-point number representing the probability that the actual duration of the operation will vary from the expected duration.
- The `duration_standard_deviation` property is a floating-point number representing the standard deviation of the duration of the operation.