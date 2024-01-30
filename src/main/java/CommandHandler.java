import java.util.Scanner;


public class CommandHandler {
    public enum Command {
        BYE,
        LIST,
        MARK,
        UNMARK,
        DELETE,
        TODO,
        DEADLINE,
        EVENT
    }

    public static boolean executeCommand(String userInput) throws DukeException {
        String[] words = userInput.split("\\s+");
        Command command = null;
        try {
            command = Command.valueOf(words[0].toUpperCase());   
        } catch (IllegalArgumentException e) {
            String commandStr = words[0];
            throw new CommandNotFoundException(commandStr);
        }

        switch (command) {
            case BYE:
                Bird.goodbye();
                return true;
            case LIST:
                TaskList.list();
                break;
            default:
                // The logic below is for commands with arguments
                String arguments = "";
                try {
                    arguments = userInput.substring(command.name().length() + 1);
                } catch (StringIndexOutOfBoundsException e) {
                    throw new ArgumentNotFoundException(command.name());
                }
                switch (command) {
                    case MARK:
                        TaskList.markTask(processTaskIdx(arguments));
                        break;
                    case UNMARK:
                        TaskList.unmarkTask(processTaskIdx(arguments));
                        break;
                    case DELETE:
                        TaskList.deleteTask(processTaskIdx(arguments));
                        break;
                    case TODO:
                        TaskList.addTask(processToDo(arguments));
                        break;
                    case DEADLINE:
                        TaskList.addTask(processDeadline(arguments));
                        break;
                    case EVENT:
                        TaskList.addTask(processEvent(arguments));
                        break;
                    default:
                        System.out.println("Error: CommandSet Hashtable contains a command that is not implemented in the switch statement!");
                        break;
                }
                // To store the updated Task List
                Storage.store();
        }
        return false;
    }
    
    private static int processTaskIdx(String arguments) throws IndexOutOfRange{
        int idx = Integer.parseInt(arguments);
        int size = TaskList.listSize();
        if (idx <= 0 || idx > size) {
            throw new IndexOutOfRange(idx, size);
        }
        return idx;
    }

    private static ToDo processToDo(String arguments) {
        return new ToDo(arguments);
    }

    private static Deadline processDeadline(String arguments) throws InvalidDeadlineFormatException, InvalidDateFormat {
        try {
            String[] parts = arguments.split("/by ");
            return new Deadline(parts[0], parts[1]);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new InvalidDeadlineFormatException();
        }
    }

    private static Event processEvent(String arguments) throws InvalidEventFormatException, InvalidDateFormat {
        try {
            String[] parts = arguments.split("/from ");
            String[] parts2 = parts[1].split("/to ");
            return new Event(parts[0], parts2[0], parts2[1]);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new InvalidEventFormatException();
        }
    }
}

