import Foundation

class Todo
{
    var title = ""
    var content = ""
    var priority = 0

    init(title: String, content: String, priority: Int)
    {
        self.title = title
        self.content = content
        self.priority = priority
    }

    func setTitle(newTitle: String)
    {
        title = newTitle
    }

    func setContent(newContent: String)
    {
        content = newContent
    }

    func setPriority(newPriority: Int)
    {
        priority = newPriority
    }
}

class Category
{
    var name = ""
    var listOfTodoItemsInThisCategory = [Todo]()

    init(name: String)
    {
        self.name = name
    }
}

var listOfTodoItems = [Todo]()
var listOfCategories = [Category]()

print("Hi! Welcome to our todo app...")
print("Please write what you want to do...")
print("createTodoItem - updateTodoItem - removeTodoItem - showAllTodoItems - sortTodoItems")
print("createCategory - addTodoItemToCategory - showAllCategoryTodoItems")

while (true)
{
    if let userInput = readLine()
    {
        if (userInput == "createTodoItem")
        {
            print("Item name:")
            let name = readLine()
            print("Item content:")
            let content = readLine()
            print("Item priority:")
            let priority = readLine()
            let todoItem = Todo(title: name!, content: content!, priority: Int(priority!)!)
            listOfTodoItems.append(todoItem)
            print("New Todo Item added.")
        }
        else if (userInput == "updateTodoItem")
        {
            print("Enter the name of item you want to update:")
            let name = readLine()
            var selectedItem = Todo(title: "", content: "", priority: -1)
            for item in listOfTodoItems
            {
                if (item.title == name)
                {
                    selectedItem = item
                }
            }
            if (selectedItem.title != "")
            {
                print("Enter new name:")
                let newName = readLine()
                print("Enter new content:")
                let newContent = readLine()
                print("Enter new priority:")
                let newPriority = readLine()
                selectedItem.setTitle(newTitle: newName!)
                selectedItem.setContent(newContent: newContent!)
                selectedItem.setPriority(newPriority: Int(newPriority!)!)
                print("Todo Item updated.")
            }
            else
            {
                print("Item with this name doesn't exist")
            }
        }
        else if (userInput == "removeTodoItem")
        {
            print("Enter the name of item you want to remove:")
            let name = readLine()
            var selectedItemIndex = -1
            for i in 0..<listOfTodoItems.count
            {
                if (listOfTodoItems[i].title == name)
                {
                    selectedItemIndex = i
                }
            }
            if (selectedItemIndex != -1)
            {
                listOfTodoItems.remove(at: selectedItemIndex)
                for category in listOfCategories
                {
                    selectedItemIndex = -1
                    for i in 0..<category.listOfTodoItemsInThisCategory.count
                    {
                        if (listOfTodoItems[i].title == name)
                        {
                            selectedItemIndex = i
                        }
                    }
                    if (selectedItemIndex != -1)
                    {
                        category.listOfTodoItemsInThisCategory.remove(at: selectedItemIndex)
                    }
                }
                print("Todo Item removed.")
            }
            else
            {
                print("Item with this name doesn't exist")
            }
        }
        else if (userInput == "sortTodoItems")
        {
            print("which property you want to sort by?")
            print("title - priority - date")
            let sortBy = readLine()
            print("Ascending or Descending?")
            print("ascending - descending")
            let sortType = readLine()

            if (sortBy == "title") 
            {
                if (sortType == "ascending") 
                {
                    listOfTodoItems = listOfTodoItems.sorted(by: { $0.title < $1.title })
                } 
                else 
                {
                    listOfTodoItems = listOfTodoItems.sorted(by: { $0.title > $1.title })
                }
            } 
            else if (sortBy == "priority") 
            {
                if (sortType == "ascending") 
                {
                    listOfTodoItems = listOfTodoItems.sorted(by: { $0.priority < $1.priority })
                } 
                else 
                {
                    listOfTodoItems = listOfTodoItems.sorted(by: { $0.priority > $1.priority })
                }
            } 
            else {
                if (sortType == "ascending") 
                {
                    listOfTodoItems = listOfTodoItems.sorted(by: { $0.dateCreated < $1.dateCreated })
                } 
                else 
                {
                    listOfTodoItems = listOfTodoItems.sorted(by: { $0.dateCreated > $1.dateCreated })
                }
            }

            for item in listOfTodoItems 
            {
                print(item.title + " - " + item.content + " - " + String(item.priority))
            }

        }
        else if (userInput == "createCategory")
        {
            print("Category name:")
            let name = readLine()
            var isNameRepititive = false
            for category in listOfCategories
            {
                if (category.name == name)
                {
                    isNameRepititive = true
                }
            }
            if (!isNameRepititive)
            {
                let category = Category(name: name!)
                listOfCategories.append(category)
                print("New Category added.")
            }
            else
            {
                print("A category with this name already exists.")
            }
        }
        else if (userInput == "addTodoItemToCategory")
        {
            print("Item name:")
            let itemName = readLine()
            var selectedItem = Todo(title: "", content: "", priority: -1)
            for item in listOfTodoItems
            {
                if (item.title == itemName)
                {
                    selectedItem = item
                }
            }
            if (selectedItem.title != "")
            {
                print("Category name:")
                let categoryName = readLine()
                var selectedCategory = Category(name: "")
                for category in listOfCategories
                {
                    if (category.name == categoryName)
                    {
                        selectedCategory = category
                    }
                }
                if (selectedCategory.name != "")
                {
                    selectedCategory.listOfTodoItemsInThisCategory.append(selectedItem)
                    print("Item added to the Category")
                }
                else
                {
                    print("Category with this name doesn't exist")
                }
            }
            else
            {
                print("Item with this name doesn't exist")
            }
        }
        else if (userInput == "showAllCategoryTodoItems")
        {
            print("Category name:")
            let categoryName = readLine()
            var selectedCategory = Category(name: "")
            for category in listOfCategories
            {
                if (category.name == categoryName)
                {
                    selectedCategory = category
                }
            }
            if (selectedCategory.name != "")
            {
                for item in selectedCategory.listOfTodoItemsInThisCategory
                {
                    print(item.title + " - " + item.content + " - " + String(item.priority))
                }
            }
            else
            {
                print("Category with this name doesn't exist")
            }
        }
        else
        {
            for item in listOfTodoItems
            {
                print(item.title + " - " + item.content + " - " + String(item.priority))
            }
        }
    }
}