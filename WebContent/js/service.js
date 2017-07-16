app.factory('homeFactory', ['$http', '$q', function($http, $q){
	 var REST_SERVICE_URI = 'http://10.0.26.172:7001/UITest/home/home/download';
	 var REST_SERVICE_URI_1 = 'resume.naukrikart.com/home/home/sendMail';
	 var factory = {
			 downloadProfile: downloadProfile,
			 sendMail:sendMail
		    };
		 
		    return factory;
		    function downloadProfile() {
		    	var deferred = $q.defer();
		    	$http.get(REST_SERVICE_URI,null)
		            .then(
		            function (response) {
		            	console.log(response.data);
		                deferred.resolve(response.data);
		            },
		            function(errResponse){
		                console.error('Error');
		                deferred.reject(errResponse);
		            }
		        );
		        return deferred.promise;
		    };	    
		    function sendMail(user) {
		    	var deferred = $q.defer();
		    	$http.post(REST_SERVICE_URI_1,user)
		            .then(
		            function (response) {
		            	console.log(response.data);
		                deferred.resolve(response.data);
		            },
		            function(errResponse){
		                console.error('Error');
		                deferred.reject(errResponse);
		            }
		        );
		        return deferred.promise;
		    };
}]);