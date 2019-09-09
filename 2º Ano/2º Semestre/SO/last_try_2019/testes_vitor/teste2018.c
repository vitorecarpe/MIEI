






int main(int argc, char** argv){

	char buf[128];
	char out[128];
	pid_t pid, pid2;

	while((readln(0, buf, 128))>0){


		if((pid=fork())==0){

			if((pid2=fork())==0){

				close(fd[0]);
				dup2(fd[1],1);
				close(fd[1]);

				execlp(buf, buf, NULL);



			}else{

				int n; 
				wait(NULL);
				close(fd[1]);
				n=read(fd[0],out,128);
				close(fd[0]);
				if(n==1 && strcmp(atoi(out[0]))==0){
					break();
				}



			}
		}
	}
	return 0;
}




int main(int argc, char** argv){

	for(int p=0; p<argv[1]; p++){
		if((ppid=fork())==0){
			close(fd[0]);
			dup2(fd[1],1);
			close(fd[1]);
			execlp("produtor", "produtor", NULL);

		}
	}

	for(int c=0; c<argv[2]; c++){
		if((cpid=fork())==0){
			close(fd[1]);
			dup2(fd[0],0);
			close(fd[1]);
			execlp("consumidor", "consumidor", NULL);

		}
	}
}



















