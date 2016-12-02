create sequence hibernate_sequence start 1 increment 1;

    create table CommentsOnTask (
        commentId int4 not null,
        commentDesc varchar(255),
        createDate timestamp,
        isDelete boolean not null,
        taskComments_id int4,
        userComment_id int4,
        primary key (commentId)
    );

    create table Files (
        fileId int4 not null,
        fileName varchar(255),
        filePath varchar(255),
        fileSize varchar(255),
        fileType varchar(255),
        isDelete boolean not null,
        uploadDate timestamp,
        taskFiles_id int4,
        primary key (fileId)
    );

    create table priorityOfTask (
        id int4 not null,
       
        priorityType varchar(255),
        primary key (id)
    );

    create table project_type (
        id int4 not null,
        isDelete boolean not null,
        projectType varchar(255),
        primary key (id)
    );

    create table Projects (
        id int4 not null,
        createdDate timestamp,
        isDelete boolean not null,
        projectDescription varchar(255),
        projectGitHubLink varchar(255),
        projectName varchar(255),
        createdUser_id int4,
        projectType_id int4,
        primary key (id)
    );

    create table Projects_Users (
        projects_id int4 not null,
        usersRelatedProject_id int4 not null,
        primary key (projects_id, usersRelatedProject_id)
    );

    create table statusOfTask (
        id int4 not null,
       
        statusName varchar(255),
        primary key (id)
    );

    create table task_Activity (
        id int4 not null,
        complete boolean not null,
        endTime timestamp,
        startTime timestamp,
        activityOfTask_id int4,
        activityOfTaskByUser_id int4,
        primary key (id)
    );

    create table Tasks (
        id int4 not null,
        createDate timestamp,
        endDate timestamp,
        isDelete boolean not null,
        startDate timestamp,
        taskDescription varchar(255),
        taskName varchar(255),
        projectTasks_id int4,
        statusTasks_id int4,
        taskPriority_id int4,
        userTasks_id int4,
        primary key (id)
    );

    create table user_role (
        id int4 not null,
        isDelete boolean not null,
        userRole varchar(255),
        primary key (id)
    );

    create table Users (
        id int4 not null,
        createDate timestamp,
        emailAddress varchar(255),
        isDelete boolean not null,
        password varchar(255),
        phoneNumber varchar(255),
        userName varchar(255),
        userRole_id int4,
        primary key (id)
    );

    