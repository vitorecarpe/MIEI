#include <stdlib.h>
#include <stdio.h>
#include <unistd.h>
#include <fcntl.h>

void main(int argc, char** argv) {
	if (argc != 2) {
		perror("não foram passados argumentos suficientes!");
		exit(-1);
	}
	char c = 'a';
	int file = open(argv[1], O_CREAT | O_WRONLY, 0600);
	for (int i = 0; i < 10000000; i++) write(file, &c, 1);
	exit(0);
}

/* The open() function shall establish the connection between a
 * file and a file descriptor. It shall create an open file description
 * that refers to a file and a file descriptor that refers to that open
 * file description. The file descriptor is used by other I/O functions
 * to refer to that file. The path argument points to a pathname naming
 * the file.
 * The open() function shall return a file descriptor for the named
 * file that is the lowest file descriptor not currently open for that
 * process. The open file description is new, and therefore the file
 * descriptor shall not share it with any other process in the system.
 * The FD_CLOEXEC file descriptor flag associated with the new file
 * descriptor shall be cleared.
 */

/*
 * int open(const char *path, int oflag [, mode]);
 * Values for oflag are constructed by a bitwise-inclusive OR of flags
 * from the following list, defined in <fcntl.h>. Applications shall
 * specify exactly ONE of the first three values (file access modes)
 * below in the value of oflag:

 * O_RDONLY
 * Open for reading only.
 * O_WRONLY
 * Open for writing only.
 * O_RDWR
 * Open for reading and writing. The result is undefined if this
 * flag is applied to a FIFO.

 * Any combination of the following may be used:
 * O_APPEND
 * If set, the file offset shall be set to the end of the file prior
 * to each write.
 * O_CREAT
 * If the file exists, this flag has no effect except as noted under
 * O_EXCL below. Otherwise, the file shall be created; the user ID
 * of the file shall be set to the effective user ID of the process;
 * the group ID of the file shall be set to the group ID of the file's
 * parent directory or to the effective group ID of the process; and
 * the access permission bits (see <sys/stat.h>) of the file mode shall
 * be set to the value of the third argument taken as type mode_t
 * modified as follows: a bitwise AND is performed on the file-mode
 * bits and the corresponding bits in the complement of the process'
 * file mode creation mask. Thus, all bits in the file mode whose
 * corresponding bit in the file mode creation mask is set are cleared.
 * When bits other than the file permission bits are set, the effect is
 * unspecified. The third argument does not affect whether the file is
 * open for reading, writing, or for both. Implementations shall provide
 * a way to initialize the file's group ID to the group ID of the parent
 * directory. Implementations may, but need not, provide an
 * implementation-defined way to initialize the file's group ID to the
 * effective group ID of the calling process.
 * O_DSYNC
 * [SIO] [Option Start] Write I/O operations on the file descriptor shall
 * complete as defined by synchronized I/O data integrity completion.
 * [Option End]
 * O_EXCL
 * If O_CREAT and O_EXCL are set, open() shall fail if the file exists.
 * The check for the existence of the file and the creation of the file
 * if it does not exist shall be atomic with respect to other threads
 * executing open() naming the same filename in the same directory
 * with O_EXCL and O_CREAT set. If O_EXCL and O_CREAT are set, and path
 * names a symbolic link, open() shall fail and set errno to [EEXIST],
 * regardless of the contents of the symbolic link. If O_EXCL is set and
 * O_CREAT is not set, the result is undefined.
 * O_NOCTTY
 * If set and path identifies a terminal device, open() shall not cause
 * the terminal device to become the controlling terminal for the process.
 * O_NONBLOCK
 * When opening a FIFO with O_RDONLY or O_WRONLY set:
 * - If O_NONBLOCK is set, an open() for reading-only shall return without
 * delay. An open() for writing-only shall return an error if no process
 * currently has the file open for reading.
 * - If O_NONBLOCK is clear, an open() for reading-only shall block the
 * calling thread until a thread opens the file for writing. An open()
 * for writing-only shall block the calling thread until a thread opens
 * the file for reading.
 * When opening a block special or character special file that supports
 * non-blocking opens:
 * - Iff O_NONBLOCK is set, the open() function shall return without
 * blocking for the device to be ready or available. Subsequent behavior
 * of the device is device-specific.
 * - If O_NONBLOCK is clear, the open() function shall block the calling
 * thread until the device is ready or available before returning.
 * Otherwise, the behavior of O_NONBLOCK is unspecified.

 * O_RSYNC
 * [SIO] [Option Start] Read I/O operations on the file descriptor shall
 * complete at the same level of integrity as specified by the O_DSYNC and
 * O_SYNC flags. If both O_DSYNC and O_RSYNC are set in oflag, all I/O
 * operations on the file descriptor shall complete as defined by
 * synchronized I/O data integrity completion. If both O_SYNC and O_RSYNC
 * are set in flags, all I/O operations on the file descriptor shall
 * complete as defined by synchronized I/O file integrity completion.
 * [Option End]
 * O_SYNC
 * [SIO] [Option Start] Write I/O operations on the file descriptor shall
 * complete as defined by synchronized I/O file integrity completion.
 * [Option End]
 * O_TRUNC
 * If the file exists and is a regular file, and the file is successfully
 * opened O_RDWR or O_WRONLY, its length shall be truncated to 0, and
 * the mode and owner shall be unchanged. It shall have no effect on
 * FIFO special files or terminal device files. Its effect on other file
 * types is implementation-defined. The result of using O_TRUNC with
 * O_RDONLY is undefined.

 * If O_CREAT is set and the file did not previously exist, upon
 * successful completion, open() shall mark for update the st_atime,
 * st_ctime, and st_mtime fields of the file and the st_ctime and st_mtime
 * fields of the parent directory.

 * If O_TRUNC is set and the file did previously exist, upon successful
 * completion, open() shall mark for update the st_ctime and st_mtime
 * fields of the file.
 */
