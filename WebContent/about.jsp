<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="head.jsp"></jsp:include>
<title>5 Megs: About</title>
</head>
<body>
<div class="container-fluid">
<div>
<h3>What is 5 Megs?</h3>
5 Megs is a discussion site like many others in the Internet (OK, it's a Reddit clone). <strong>However</strong>, there's a difference: the data is stored on your computer.
<h3>Stored where?</h3>
The data for the site is stored on your computer, using Local Storage, an HTML 5 functionality that allow web sites to a (little bit) of data on a user's machine.
<h3>What is stored?</h3>
Everything that you either uploaded yourself (a post or a comment) or upvoted.
<h3>How Long?</h3>
Until you downvote it, or until you run out of Local Storage space. When you downvote something, it gets removed from your machine. When you run out of space, the oldest thing stored is removed to make space for the newer content.
<h3>Downvote?</h3>
You can upvote everything once, which means it gets stored on your machine. You can only downvote something you've upvoted before.
<h3>If everything in on our machines, what do you store?</h3>
Nothing! All data lives in the server memory but nothing is persisted in a database. Once you leave the site, after a certain time all that you upvoted loses your upvotes. When you come back, everything stored on your machine is uploaded again. So stories may disappear when people leave the site and come back later with them.
<h3>Why the name 5 Megs?</h3>
5 Megabytes is roughly what browsers allow in Local Storage size for each site.
<h3>What's behind a pseudo?</h3>
When you post a story or a comment, we ask for a pseudo. Since we don't have a database, we have no list of pseudonyms, no passwords, etc. A pseudo is a handle, but never think that the same pseudo is always the same person, and vice versa. This is the Internet, deal with it.
<h3>Why, but why?</h3>
First of all, because it's fun! Also, there may be value in having the data on your machine, meaning you can easily copy it, etc. Even if the server decides to censor stories, every upvoted story and comment is at least on two client computers (the person's who's posted it and the person's who upvoted it).
<h3>Plz send teh codez!</h3>
<a href="https://github.com/JPMoresmau/5megs" target="_new">https://github.com/JPMoresmau/5megs</a>
</div>
<jsp:include page="foot.jsp"></jsp:include>

</div>
</body>
</html>