# Talk Together Project



## 1. 💻 Framework

- SprintBoot
- Vue
- MySQL

## 2. 📦 Install

### Github

```bash
git clone https://github.sydney.edu.au/ELEC5619-Group-26/ELEC5619-2021S2-G26.git
cd ELEC5619-2021S2-G26-master
```
### Description

Talk Toghther System is a real-time chat system that allows user to perform random matching chat with a stranger, chat with friends and join existing group rooms to chat with multi-person who has similar interest. 

## 3.Setup database

1. Please find the file with the .sql suffix in the elec5619 folder we have provided

2. Open your mysql gui tools, such as datagrip etc.

3. Create a schema called elec5619

4. Import the sql file from the elec5619 folder into the elec5619 schema


## 4. Set up back-end

1. Use Intellij to open elec5619 folder (You can drag and drop the elec5619 folder directly onto the intellij icon)
2. Open src/main/resoucres, open application.yml file, 

   - set url to "jdbc:mysql://localhost:3306/elec5619"
   - set username and password to your own database username and password
3. Run the ServerApplication main() method in WebSocketChatApplication class

4. Please make sure that your port 3001 is not occupied


## 5. Project setup(Front-end)

1. Make sure you have node.js installed on your mac or pc

2. If you have not downloaded node.js, please visit the following website to do so. If you have node.js on your computer, please ignore this step

   https://nodejs.org/en/download/current/

3. When you've finished the installation, run npm -v in cmd or terminal, making sure you have the npm command available

   ```
   npm -v   // check npm version
   ```

4. Then use cmd or terminal enter elec5619/client folder

   ```
   cd elec5619/client
   ```

5. Before running following commands in cmd or terminal, Please make sure your port 8080 is not occupied

```
npx kill-port 8080 // if your port 8080 is occupied

npm install
npm run serve
```

6. Open browser and type http://localhost:8080/login**

## 6. APIs

- Backend
  - fastjson  -> Version: 1.3.32
  - spirng-websocket -> Version: 5.3.4
  - jacson-datatype-hibernate5 -> Version: 2.10.1
  - Java-Websocket -> Version: 1.3.0
  - Commons-codec -> Version: 1.13
  - Lombok -> version: 1.16.22
  - spring-boot-starter-mail (Java Mail) -> Version: 2.5.6
  - Spring-boot-starter-data-jpa -> version: 2.5.6
- Frontend
  - Baidu Translate API -> Version: 0.4.3
  - Vue-lazyload -> Version: 1.3.3
  - Js-md5 -> Version: 0.7.3
  - ant-design-vue -> Version: 1.7.8
  - axios -> Version: 0.18.0
  - less-loader -> Version: 7.3.0

## 7. All completed Functions

- Login 
- Sign up (New customer will receive a welcome email)
- forget password (auto send new password to customer via email)
- Random Chat
- Group Chat
- One to One Chat
- Add Friend (When user in random chat)

