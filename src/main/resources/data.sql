show databases;
use casestudyproject;
INSERT INTO parent_task(parent_task) values("-N/A-");
INSERT INTO parent_task(parent_task) values("Parent Task Main");

INSERT INTO child_task
(child_task,end_date,end_task,priority,start_date,parent_taskid)
values('Data Base Training','2019-2-20',true,5,'2019-1-5',2);
INSERT INTO child_task
(child_task,end_date,end_task,priority,start_date,parent_taskid)
values('Meet Manager','2019-1-6',false,5,'2019-1-6',1);

