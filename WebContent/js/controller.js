app.controller('homeController', function ($scope, $rootScope, $http,$window,$sessionStorage,$state, $location,homeFactory) {
	$scope.user={};
	$scope.download=function(){
		var pathString =window.location.protocol+"//"+window.location.host+window.location.pathname;
		var url=pathString+'home/home/download';
		var xhr=new XMLHttpRequest();
		xhr.open("GET",url);
		xhr.setRequestHeader("Content-Type","application/msword;charset=UTF-8");
		xhr.responseType="arraybuffer";
		xhr.onload=function(){
			if(this.status==200){
				var blob =new Blob([xhr.response],{type:'application/msword'});
				var a=document.createElement('a');
				a.href=URL.createObjectURL(blob);
				a.download="tushar_resume.doc";
				a.click();
			}
		};
		xhr.send(null);
		/*homeFactory.downloadProfile()
        .then(
        		function(data) {
        			console.log(data);
        		},
                function(errResponse){
                    console.error('Error');
                });*/
		
	}
	$scope.sendMessage=function(){
		homeFactory.sendMail($scope.user)
        .then(
        		function(data) {
        			console.log(data);
        		},
                function(errResponse){
                    console.error('Error');
                });
		
		console.log($scope.user);
	}
});