import Foundation 
 
class Todo { 
    var title = "" 
    var content = "" 
    var priority = 0 
 
    init(title: String, content: String, priority: Int) { 
        self.title = title 
        self.content = content 
        self.priority = priority 
    } 
 
    func setTitle(newTitle: String) { 
        title = newTitle 
    } 
 
    func setContent(newContent: String) { 
        content = newContent 
    } 
 
    func setPriority(newPriority: Int) { 
        priority = newPriority 
    }
} 

var listOfTodoItems = [Todo]()

print("Hi! Welcome to our todo app...") 
print("Please write what you want to do...") 
print("createTodoItem - updateTodoItem - removeTodoItem - showAllTodoItems") 

let userInput = readLine()
if (userInput == "createTodoItem") { 
    print("Item name:") 
    let name = readLine()
    print("Item content:") 
    let content = readLine() 
    print("Item priority:") 
    let priority = readLine()
    let todoItem = Todo(title: name, content: content, priority: Int(priority)) 
    listOfTodoItems.append(todoItem) 
}
else if (userInput == "updateTodoItem") { 
    print("Enter the name of item you want to update:") 
    let name = readLine()
    var selectedItem = Todo(title: "", content: "", priority: -1)
    for item in listOfTodoItems { 
        if (item.title == name) {
            selectedItem = item 
        } 
    }
    if (selectedItem.title != "") { 
        print("Enter new name:") 
        let newName = readLine()
        print("Enter new content:") 
        let newContent = readLine() 
        print("Enter new priority:") 
        let newPriority = readLine()
        selectedItem.setTitle(newTitle: newName) 
        selectedItem.setContent(newContent: newContent) 
        selectedItem.setPriority(newPriority: Int(newPriority))         
    } else { 
        print("Item with this name doesn't exist") 
    } 
}
else if (userInput == "removeTodoItem") { 
    print("Enter the name of item you want to remove:") 
    let name = readLine() 
    var selectedItemIndex = -1
    for i in 0..<listOfTodoItems.count { 
        if (listOfTodoItems[i].title == name) { 
            selectedItemIndex = i 
        } 
    } 
    if (selectedItemIndex != -1) { 
        listOfTodoItems.remove(at: selectedItemIndex) 
    } else { 
        print("Item with this name doesn't exist") 
    } 
} else { 
    for item in listOfTodoItems { 
        print(item.title + "-" + item.content + "-" + String(item.priority)) 
    } 
}