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
			if (link && link.toLowerCase().startsWith("javascript")){
				alert("no javascript links, thanks!");
				return false;
			}
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
			this.uploadData(s,"index.jsp");
			return false;
		},
		commentForm:function(frm,page){
			if (!this.hasStorage()){
				alert("You don't have storage capabilities!");
				return false;
			}
			var pseudo=frm["pseudo"].value;
			if(!pseudo){
				alert("Enter a pseudo!");
				return false;
			}
			var text=frm["text"].value;
			if(!text){
				alert("Enter some text!");
				return false;
			}
			var mother=frm["mother"].value;
			var obj={'p':pseudo,'m':mother,'d':new Date().getTime(),'t':text};
			var s=JSON.stringify(obj);
			var hash=s.hashCode();
			this.store(this.storage(),hash,s);
			var arr=new Array();
			arr.push(s);
			s=JSON.stringify(arr);
			this.uploadData(s,page);
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
					this.uploadData(s,"index.jsp");					
				}

			}
		},
		uploadData:function(s,page){
			$.post("upload",s,function(){
				location.href=page;
			});
		},
		upvote:function(s){
			var st=this.storage();
			var me=this;
			$.post("upvote",{"k":s},function(r){
				if (r && r.length>0){
			        var hash=r.hashCode();
					me.store(st,hash,r);	
					$("#u_"+s).hide();
					$("#d_"+s).show();
					var txt=$("#s_"+s).text();
					var i=parseInt(txt)+1;
					$("#s_"+s).text(i);
				}
			},'text');
			
			
		},
		downvote:function(s){
			var st=this.storage();
			$.post("downvote",{"k":s},function(r){
				if (r && r.length>0){
			        var hash=r.hashCode();
					st.removeItem(hash);		
					var txt=$("#s_"+s).text();
					var i=parseInt(txt)-1;
					if (i>0){
						$("#u_"+s).show();
						$("#d_"+s).hide();
						$("#s_"+s).text(i);
			        } else {
			        	$("#a_"+s).hide();
			        }
		        }
			},'text');
			
		},
		clear:function(s){
			var st=this.storage();
			$.post("clear",function(){
				st.clear();
		        location.href="index.jsp";
			},'text');
		}
		
};