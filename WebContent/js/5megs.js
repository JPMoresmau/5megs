/**
 * 
 */
var _5megs={
		hasStorage:function(){
			return this.storage()!=null;
		},
		storage:function() {
			  try {
			    if ('localStorage' in window && window['localStorage'] !== null){
			    	return window['localStorage'];
			    }
			  } catch (e) {
			  }
 		      return null;

			},
		storeForm:function(frm){
			if (!this.hasStorage()){
				alert("You don't have storage capabilities!");
				return false;
			}
			var pseudo=frm["pseudo"].value;
			if(!pseudo){
				alert("Enter a pseudo!");
				return false;
			}
			var title=frm["title"].value;
			if(!title){
				alert("Enter a title!");
				return false;
			}
			var link=frm["link"].value;
			var text=frm["text"].value;
			var obj={'p':pseudo,'h':title,'d':new Date().getTime()};
			
			if (link){
				obj.l=link;
			}
			if (text){
				obj.t=text;
			}
			var s=JSON.stringify(obj);
			var hash=s.hashCode();
			this.store(this.storage(),hash,s);
			var arr=new Array();
			arr.push(s);
			s=JSON.stringify(arr);
			this.uploadData(s,true);
			return false;
		},
		store:function(st,key,contents){
			try {
				st[key]=contents;	
			} catch (e){
				if (e.name.toUpperCase().contains("QUOTA") && st.length>0){
					var arr=new Array();
					var oldestK=null;
					var oldest=new Date().getTime();
					for (var a=0;a<st.length;a++){
						var k=st.key(a);
						var s=st[k];
						var obj = JSON.parse(s);
						if (obj.d && obj.d<oldest){
							oldest=obj.d;
							oldestK=k;
						}
					}
					if (oldestK){
						downvote(oldestK);
						st.removeItem(oldestK); // downvote is asynchronous, so clear now
						store(st,key,contents);						
					}

				}
			}
			
		},
		upload:function(){
			var st=this.storage();
			if (st!=null){
				var arr=new Array();
				for (var a=0;a<st.length;a++){
					arr.push(st[st.key(a)]);
				}
				if (arr.length>0){
					var s=JSON.stringify(arr);
					this.uploadData(s,false);					
				}

			}
		},
	    createXHR:function(){
		    var xhr;
		    if (window.ActiveXObject) {
		        try {
		            xhr = new ActiveXObject("Microsoft.XMLHTTP");
		        } catch(e) {
		            alert(e.message);
		            xhr = null;
		        }
		    } else {
		        xhr = new XMLHttpRequest();
		    }
		    return xhr;
		},
		uploadData:function(s,reload){
			var xhr = this.createXHR();
			//if (reload){
				xhr.onreadystatechange = function(){
				    if (xhr.readyState === 4) {
				        location.href="index.jsp";
				    }
				}
			//}
			xhr.open('POST', 'upload', true);
			xhr.setRequestHeader("Content-type", "application/json");
			xhr.send(s);
		},
		upvote:function(s){
			var xhr = this.createXHR();
			var st=this.storage();
			xhr.onreadystatechange = function(){
			    if (xhr.readyState === 4) {
			        var r=xhr.responseText;
			        if (r.length>0){
				        var hash=r.hashCode();
						this.store(st,hash,r);			
						var up=document.getElementById("u_"+s);
						up.style.display='none';
						var dp=document.getElementById("d_"+s);
						dp.style.display='inline';
						var sc=document.getElementById("s_"+s);
						var txt=sc.textContent;
						var i=parseInt(txt);
						sc.textContent=""+(i+1);
			        }

			    }
			}
			xhr.open('POST', 'upvote', true);
			xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
			xhr.send("k="+encodeURIComponent(s));
			
		},
		downvote:function(s){
			var xhr = this.createXHR();
			var st=this.storage();
			xhr.onreadystatechange = function(){
			    if (xhr.readyState === 4) {
			        var r=xhr.responseText;
			        if (r.length>0){
				        var hash=r.hashCode();
						st.removeItem(hash);			
						var up=document.getElementById("u_"+s);
						up.style.display='inline';
						var dp=document.getElementById("d_"+s);
						dp.style.display='none';
						var sc=document.getElementById("s_"+s);
						var txt=sc.textContent;
						var i=parseInt(txt);
						sc.textContent=""+(i-1);
			        }

			    }
			}
			xhr.open('POST', 'downvote', true);
			xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
			xhr.send("k="+encodeURIComponent(s));
			
		},
		clear:function(s){
			var xhr = this.createXHR();
			var st=this.storage();
			xhr.onreadystatechange = function(){
			    if (xhr.readyState === 4) {
			        var r=xhr.responseText;
			        st.clear();
			        location.href="index.jsp";
			    }
			}
			xhr.open('POST', 'clear', true);
			xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
			xhr.send("");
			
		}
		
};