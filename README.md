# D.E.S.S.

*Discrete Event Simulation System*
for Industrial Scheduling Plans

# Installation
## Dependencies/Necessary Installs
- JDK Java 17
- Maven
- Node
- npm
- npm serve (e.g. via `npm install -g serve`)

## Building the Backend 
1. Setup a local MySQL Database (no need to setup any tables, the app does it automatically)
2. Go to `DiscreteEventSimulation/src/main/resources/application.properties` and put in database information
3. `cd` into `DiscreteEventSimulation`
4. Run `./mvnw clean package`

## Building the Frontend
1. Open `web-frontend/src/App.js`
2. Configure baseUrl to Server/Backend URL (where the backend jar will be running, on local machine it will be the default value `https://localhost:8080`)
3. `cd` into `web-frontend`
4. Run `npm run build`

# Running
1. Start the backend with `java -jar DiscreteEventSimulation/target/DiscreteEventSimulation-0.0.1-SNAPSHOT.jar`
2. Start the frontend with `serve -s build` from `web-frontend/`
3. Path to the application will be printed in the frontend console