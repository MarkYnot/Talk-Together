# 5619 Together Project


# 💻 Framework

- SprintBoot
- Vue
- MySQL

# 📦 Install
```bash
git clone https://github.sydney.edu.au/ELEC5619-Group-26/ELEC5619-2021S2-G26.git
cd ELEC5619-2021S2-G26-master
```
### Project setup(Front-end)

**1. Use terminal "cd" into 5619Project file, then:**

```
cd client 
npm install
npm run serve
```
**2. Open browser and type http://localhost:8080/**

  ![test](https://github.sydney.edu.au/ELEC5619-Group-26/ELEC5619-2021S2-G26/blob/master/README%20image/test.png)

### Set up back-end
> Run the ServerApplication main() method

3. **Setup database**

Modify the username and password in application.yml in resource folder

Use DataGrip or other Database GUI tools to create database.

Please make sure your schema name, table name and attributes name are same with the following guide

Schema: elec5619

table:

- User
  - user_id int   (auto increment  PK  NOT NULL)
  - email char(100) NOT NULL
  - password int NOT NULL
  - firstname char(50) NOT NULL
  - lastname char(50) NOT NULL
  - state char(10)
  - photo nvarchar(10000)
  - background nvarchar(100000)
  - username char(100)
- Tag
  - Tag_id int  (auto increment  PK  NOT NULL)
  - Tag_name char(100) NOT NULL
- Group_Chat
  - room_id int  (auto increment  PK  NOT NULL)
  - Tag_id  int NOT NULL  FK  reference to Tag(tag_id)
- Friends
  - friend_id  int   (auto increment  PK  NOT NULL)
  - user_id int NOT NULL     FK  reference to User(user_id)
  - user_id1 int  NOT NULL    FK  reference to User(user_id)
- Chat_Room
  - room_id int  (auto increment  PK  NOT NULL)
  - room_name char(50)
  - user_id int NOT NULL     FK  reference to User(user_id)
  - user_id1 int  NOT NULL    FK  reference to User(user_id)
- User_To_Group
  - id  int  (auto increment  PK  NOT NULL)
  - user_id int NOT NULL     FK  reference to User(user_id)
  - room_id int NOT NULL     FK  reference to Group_Chat(room_id)
- User_To_Tag
  - id  int  (auto increment  PK  NOT NULL)
  - user_id int NOT NULL     FK  reference to User(user_id)
  - tag_id  int NOT NULL     FK  reference to Tag(tag_id)

