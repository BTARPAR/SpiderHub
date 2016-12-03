-- NECESSARY TO TEST SPIDERHUB

INSERT INTO public.user_role(
	id, isdelete, userrole)
	VALUES (1000, 'no', 'ROLE_ADMIN');
INSERT INTO public.user_role(
	id, isdelete, userrole)
	VALUES (1001, 'no', 'ROLE_MANAGER');
INSERT INTO public.user_role(
	id, isdelete, userrole)
	VALUES (1002, 'no', 'ROLE_MEMBER');
	
INSERT INTO public.users(
	id, createdate, emailaddress, isdelete, password, phonenumber, username, userrole_id)
	VALUES (1,'10/28/2016','student1@localhost.localdomain',false,'abcd','(416) 477-9856','Member1',1002);

INSERT INTO public.users(
	id, createdate, emailaddress, isdelete, password, phonenumber, username, userrole_id)
	VALUES (2,'10/28/2016','manager1@localhost.localdomain',false,'abcd','(417) 477-9856','Manager1',1001);

INSERT INTO public.users(
	id, createdate, emailaddress, isdelete, password, phonenumber, username, userrole_id)
	VALUES (3,'10/28/2016','admin1@localhost.localdomain',false,'abcd','(418) 477-9856','Admin1',1000);
	
INSERT INTO public.project_type(
	id, isdelete, projecttype)
	VALUES (101, false, 'Software Project');
	
INSERT INTO public.project_type(
	id, isdelete, projecttype)
	VALUES (102, false, 'Business Project');
	
INSERT INTO public.project_type(
	id, isdelete, projecttype)
	VALUES (103, false, 'business');
	
INSERT INTO public.projects(
	id, createddate, isdelete, projectdescription, projectgithublink, projectname, createduser_id, projecttype_id)
	VALUES (1,'10/28/2016',false,'project description 1','webapp@localhost.localdomain','webapp1',2, 100);
	
INSERT INTO public.projects(
	id, createddate, isdelete, projectdescription, projectgithublink, projectname, createduser_id, projecttype_id)
	VALUES (2,'10/28/2016',false,'project description 2','businessapp@localhost.localdomain','businessapp1',2, 101);
	
INSERT INTO public.projects_users(
	projects_id, usersrelatedproject_id)
	VALUES (1, 1);
	
INSERT INTO public.projects_users(
	projects_id, usersrelatedproject_id)
	VALUES (2, 1);
	
INSERT INTO public.priorityoftask(
	id, prioritytype)
	VALUES (1, 'high');
    
INSERT INTO public.priorityoftask(
	id, prioritytype)
	VALUES (2, 'medium');
    
INSERT INTO public.priorityoftask(
	id, prioritytype)
	VALUES (3, 'low');
    
INSERT INTO public.statusoftask(
	id, statusname)
	VALUES (1, 'on going');
    
INSERT INTO public.statusoftask(
	id, statusname)
	VALUES (2, 'finished');