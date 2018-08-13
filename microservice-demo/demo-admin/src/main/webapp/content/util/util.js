function getDictEntryList(name){
	var se = $('select[name='+name+']');
	if(se != null && se.length > 0){
		for(var i=0;i<se.length;i++){
			var obj = se[i];
			var name = obj.name;
			$.ajax({
				url:"/dict/getDictEntryListToSelect",
				type:"post",
				data:{dicttypeid:name},
				cache: false,
				async: false,
				success:function(text){
					var returnJson =JSON.parse(text);
					if(returnJson.responseCode == 0){
						var entryList = returnJson.entryList;
						for(var j=0;j<entryList.length;j++){
							obj.options.add(new Option(entryList[j].dictname,entryList[j].dictid));
						}
					}
				}
			});
		}
	}
}


