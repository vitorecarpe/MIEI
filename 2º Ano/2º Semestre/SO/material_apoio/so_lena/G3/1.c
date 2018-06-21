#include <unistd.h>
#include <stdlib.h>
#include <stdio.h>

int main(int argc, char** argv) {
	//execlp("ls", "ls", "-l", NULL); // procura no path
	//execl("/bin/ls", "ls", "-l", NULL); // procura na pasta dada
	argv[0] = "ls";
	argv[1] = "-l";
	argv[2] = NULL;
	execvp("ls", argv); // procura no path e executa dada a lista inteira
	exit(1);
}

/*
 * wait() <- on success returns the process ID of
 * the terminated child
 * <DEFUNCT> the process has finished but has not yet
 * been waited on (ZOMBIE PROCESS) (ainda tem valores
 * dos quais precisamos)
 */
/*
 * The exec() family of functions replaces the current
 * process image with a new process image.
 * The initial argument for these functions is the
 * name of a file that is to be executed.
 * The  const  char *arg  and subsequent ellipses
 * in the execl(), execlp(), and execle() functions
 * can be thought of as arg0, arg1, ..., argn.
 * Together they describe a list of one  or
 * more  pointers to null-terminated strings that
 * represent the argument list available to the
 * executed program.  The first argument, by
 * convention, should point to the filename
 * associated  with  the  file  being  executed.
 * The list of arguments must be terminated by a null
 * pointer, and, since these are variadic functions,
 * this pointer must be cast (char *) NULL.
 * The execv(), execvp(), and execvpe() functions
 * provide an array of pointers to  null-terminated
 * strings  that  represent  the argument list
 * available to the new program.  The first argument,
 * by convention, should point to the filename
 * associated with the file  being  executed.
 * The array of pointers must be terminated by a null
 * pointer.
 * The  execle()  and  execvpe()  functions allow
 * the caller to specify the environment of the
 * executed program via the argument envp.  The envp
 * argument is an array of pointers to null-
 * terminated  strings and must be terminated by a
 * null pointer.  The other functions take the
 * environment for the new process image from
 * the external variable  environ  in  the  calling
 * process.
 */

/*
 * The calls with v in the name take an array parameter
 * to specify the argv[] array of the new program.
 * The calls with l in the name take the arguments of
 * the new program as a variable-length argument list
 * to the function itself.
 * The calls with e in the name take an extra argument
 * to provide the environment of the new program;
 * otherwise, the program inherits the current
 * process's environment.
 * The calls with p in the name search the PATH
 * environment variable to find the program if it
 * doesn't have a directory in it (i.e. it doesn't
 * contain a / character). Otherwise, the program
 * name is always treated as a path to the executable.
 */