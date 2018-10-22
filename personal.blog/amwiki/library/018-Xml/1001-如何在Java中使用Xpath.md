### 前言
Xpath是一种查找xml文件信息的语言。你也可以说Xpath是一种结构化查询语言，Xpath通过一个XML的document文档来回穿梭雨元素和属性之间，你也可以用java来遍历一个XML文件。

Xpath依赖于一个很强大的表达式，可以用于解析一个xml的document，并且获取相关的信息。

下面我们看一个xml的文件，里面包含了员工的一些信息。
```xml
<?xml version="1.0"?>
<Employees>
	<Employee emplid="1111" type="admin">
		<firstname>John</firstname>
		<lastname>Watson</lastname>
		<age>30</age>
		<email>johnwatson@sh.com</email>
	</Employee>
	<Employee emplid="2222" type="admin">
		<firstname>Sherlock</firstname>
		<lastname>Homes</lastname>
		<age>32</age>
		<email>sherlock@sh.com</email>
	</Employee>
	<Employee emplid="3333" type="user">
		<firstname>Jim</firstname>
		<lastname>Moriarty</lastname>
		<age>52</age>
		<email>jim@sh.com</email>
	</Employee>
	<Employee emplid="4444" type="user">
		<firstname>Mycroft</firstname>
		<lastname>Holmes</lastname>
		<age>41</age>
		<email>mycroft@sh.com</email>
	</Employee>
</Employees>
```

我们将此文件保存在路径中C:\employees.xml。我们将在演示中使用此xml文件，并尝试使用XPath获取有用的信息。在开始之前，让我们从上面的xml文件中检查一些信息。

1. 我们的xml文件中有4名员工
2. 每个员工都有一个由属性定义的唯一员工ID emplid
3. 每个员工还有一个属性type，用于定义员工是管理员还是用户。
4. 每个员工都有四个子节点：firstname，lastname，age和email，年龄是一个数字

### 1.学习Java DOM解析API
为了理解XPath，首先我们需要了解Java中DOM解析的基础知识。Java以下面的API形式提供了强大的domparser实现。

#### 创建Java DOM XML Parser
首先，我们需要使用DocumentBuilderFactory类创建文档构建器。
```java
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
//...

DocumentBuilderFactory builderFactory =
        DocumentBuilderFactory.newInstance();
DocumentBuilder builder = null;
try {
    builder = builderFactory.newDocumentBuilder();
} catch (ParserConfigurationException e) {
    e.printStackTrace();  
}
```
#### 使用Java DOM Parser解析XML
一旦我们有了文档构建器对象。我们使用它来解析XML文件并创建一个文档对象。
```java
import org.w3c.dom.Document;
import java.io.IOException;
import org.xml.sax.SAXException;
//...

try {
    Document document = builder.parse(
            new FileInputStream("c:\\employees.xml"));
} catch (SAXException e) {
    e.printStackTrace();
} catch (IOException e) {
    e.printStackTrace();
}
```

在上面的代码中，我们从文件系统解析XML文件。有时，我们可能希望解析指定为String值的XML，而不是从文件中读取它。下面的代码可以很方便地解析指定为String的XML。
```java
String xml = ...;
Document xmlDocument = builder.parse(new ByteArrayInputStream(xml.getBytes()));
```
#### 创建XPath对象
一旦我们有文档对象。我们准备使用XPath。只需使用XPathFactory创建一个xpath对象。
```java
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
//...

XPath xPath =  XPathFactory.newInstance().newXPath();
```
#### 使用XPath解析XML
使用xpath对象来编译XPath表达式并在文档上对其进行评估。在下面的代码中，我们读取了员工ID = 3333的员工的电子邮件地址。此外，我们还指定了API来读取XML节点和节点列表。

```java
String expression = "/Employees/Employee[@emplid='3333']/email";

//read a string value
String email = xPath.compile(expression).evaluate(xmlDocument);

//read an xml node using xpath
Node node = (Node) xPath.compile(expression).evaluate(xmlDocument, XPathConstants.NODE);

//read a nodelist using xpath
NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(xmlDocument, XPathConstants.NODESET);
```

### 学习XPath表达式
如上所述，XPath使用路径表达式从xml文档中选择节点或节点列表。下面是一个有用的路径和表达式列表，可用于从xml文档中选择任何节点/节点列表。

| 表达 | 描述
| :------: | :------:
| nodename | 选择名称为“nodename”的所有节点
| / | 从根节点中选择
| // | 从当前节点中选择与选择匹配的文档中的节点，无论它们在何处
| . | 选择当前节点
| .. | 选择当前节点的父节点
| @ | 选择属性
| employee | 选择名为“employee”的所有节点
| employees/employee | 选择作为员工子级的所有员工元素
| //employee | 选择所有员工元素，无论它们在文档中的位置

下面的表达式列表称为Predicates。谓词用方括号[...]定义。它们用于查找特定节点或包含特定值的节点。

| 路径表达 | 结果
| :------: | :------:
| /employees/employee[1] | 选择作为employees元素的子元素的第一个employee元素。
| /employees/employee[last()] | 选择最后一个employee元素，该元素是employees元素的子元素
| /employees/employee[last()-1] | 选择最后一个employee元素，该元素是employees元素的子元素
| //employee[@type='admin'] | 选择具有名为type的属性且值为“admin”的所有employee元素

### 示例：使用XPath查询XML文档

下面是使用xpath的不同表达式从xml文档中获取一些信息的几个示例

#### 阅读所有员工的名字

下面的表达式将读取firstname所有员工。

```java
String expression = "/Employees/Employee/firstname";
System.out.println(expression);
NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(xmlDocument, XPathConstants.NODESET);
for (int i = 0; i < nodeList.getLength(); i++) {
    System.out.println(nodeList.item(i).getFirstChild().getNodeValue()); 
}
```
输出：
```java
John
Sherlock
Jim
Mycroft
```
#### 使用员工ID读取特定员工

下面的表达式将读取emplid = 2222的员工的员工信息。检查我们如何使用API​​检索节点信息，然后转发此节点以打印xml标签及其值。
```java
String expression = "/Employees/Employee[@emplid='2222']";
System.out.println(expression);
Node node = (Node) xPath.compile(expression).evaluate(xmlDocument, XPathConstants.NODE);
if(null != node) {
	nodeList = node.getChildNodes();
	for (int i = 0;null!=nodeList && i < nodeList.getLength(); i++) {
		Node nod = nodeList.item(i);
		if(nod.getNodeType() == Node.ELEMENT_NODE)
			System.out.println(nodeList.item(i).getNodeName() + " : " + nod.getFirstChild().getNodeValue()); 
	}
}
```
输出：
```java
firstname : Sherlock
lastname : Homes
age : 32
email : sherlock@sh.com
```
#### 阅读所有管理员工的名字

这也是读取firstname所有admin管理员（由type = admin定义）的谓词示例。
```java
String expression = "/Employees/Employee[@type='admin']/firstname";
System.out.println(expression);
NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(xmlDocument, XPathConstants.NODESET);
for (int i = 0; i < nodeList.getLength(); i++) {
    System.out.println(nodeList.item(i).getFirstChild().getNodeValue()); 
}
```
输出：
```java
John
Sherlock
```
#### 阅读所有年龄超过40岁的员工的姓名
了解我们如何使用谓词来过滤员工age > 40。
```java
String expression = "/Employees/Employee[age>40]/firstname";
NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(xmlDocument, XPathConstants.NODESET);
for (int i = 0; i < nodeList.getLength(); i++) {
    System.out.println(nodeList.item(i).getFirstChild().getNodeValue()); 
}
```
输出：
```java
Jim
Mycroft
```
#### 阅读前两名员工的名字（在xml文件中定义）
在谓词中，您可以使用position()来标识xml元素的位置。在这里，我们使用position（）过滤前两名员工。
```java
String expression = "/Employees/Employee[position() <= 2]/firstname";
NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(xmlDocument, XPathConstants.NODESET);
for (int i = 0; i < nodeList.getLength(); i++) {
    System.out.println(nodeList.item(i).getFirstChild().getNodeValue()); 
}
```
输出：
```java
John
Sherlock
```
### 完整的Java源代码
要执行此源，只需在IDE中创建一个基本的Java项目，或者只需将下面的代码保存在Main.java中并执行即可。它需要employees.xml文件作为输入。复制本教程开头定义的员工xml c:\\employees.xml。

```java
package net.viralpatel.java;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Main {
	public static void main(String[] args) {

		try {
			FileInputStream file = new FileInputStream(new File("c:/employees.xml"));
				
			DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
			
			DocumentBuilder builder =  builderFactory.newDocumentBuilder();
			
			Document xmlDocument = builder.parse(file);

			XPath xPath =  XPathFactory.newInstance().newXPath();

			System.out.println("*************************");
			String expression = "/Employees/Employee[@emplid='3333']/email";
			System.out.println(expression);
			String email = xPath.compile(expression).evaluate(xmlDocument);
			System.out.println(email);

			System.out.println("*************************");
			expression = "/Employees/Employee/firstname";
			System.out.println(expression);
			NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(xmlDocument, XPathConstants.NODESET);
			for (int i = 0; i < nodeList.getLength(); i++) {
			    System.out.println(nodeList.item(i).getFirstChild().getNodeValue()); 
			}

			System.out.println("*************************");
			expression = "/Employees/Employee[@type='admin']/firstname";
			System.out.println(expression);
			nodeList = (NodeList) xPath.compile(expression).evaluate(xmlDocument, XPathConstants.NODESET);
			for (int i = 0; i < nodeList.getLength(); i++) {
			    System.out.println(nodeList.item(i).getFirstChild().getNodeValue()); 
			}

			System.out.println("*************************");
			expression = "/Employees/Employee[@emplid='2222']";
			System.out.println(expression);
			Node node = (Node) xPath.compile(expression).evaluate(xmlDocument, XPathConstants.NODE);
			if(null != node) {
				nodeList = node.getChildNodes();
				for (int i = 0;null!=nodeList && i < nodeList.getLength(); i++) {
					Node nod = nodeList.item(i);
					if(nod.getNodeType() == Node.ELEMENT_NODE)
						System.out.println(nodeList.item(i).getNodeName() + " : " + nod.getFirstChild().getNodeValue()); 
				}
			}
			
			System.out.println("*************************");

			expression = "/Employees/Employee[age>40]/firstname";
			nodeList = (NodeList) xPath.compile(expression).evaluate(xmlDocument, XPathConstants.NODESET);
			System.out.println(expression);
			for (int i = 0; i < nodeList.getLength(); i++) {
			    System.out.println(nodeList.item(i).getFirstChild().getNodeValue()); 
			}
		
			System.out.println("*************************");
			expression = "/Employees/Employee[1]/firstname";
			System.out.println(expression);
			nodeList = (NodeList) xPath.compile(expression).evaluate(xmlDocument, XPathConstants.NODESET);
			for (int i = 0; i < nodeList.getLength(); i++) {
			    System.out.println(nodeList.item(i).getFirstChild().getNodeValue()); 
			}
			System.out.println("*************************");
			expression = "/Employees/Employee[position() <= 2]/firstname";
			System.out.println(expression);
			nodeList = (NodeList) xPath.compile(expression).evaluate(xmlDocument, XPathConstants.NODESET);
			for (int i = 0; i < nodeList.getLength(); i++) {
			    System.out.println(nodeList.item(i).getFirstChild().getNodeValue()); 
			}

			System.out.println("*************************");
			expression = "/Employees/Employee[last()]/firstname";
			System.out.println(expression);
			nodeList = (NodeList) xPath.compile(expression).evaluate(xmlDocument, XPathConstants.NODESET);
			for (int i = 0; i < nodeList.getLength(); i++) {
			    System.out.println(nodeList.item(i).getFirstChild().getNodeValue()); 
			}

			System.out.println("*************************");

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}		
	}
}
```
