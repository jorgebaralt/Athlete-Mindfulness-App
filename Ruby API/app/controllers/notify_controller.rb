class NotifyController < ApplicationController



	def create


		
		client = Twilio::REST::Client.new(ENV['account_sid'], ENV['auth_token'])

		service = client.notify.v1.services(ENV['notify_service_sid'])

		identity = params[:identity]

		# credential = client.notify.v1.credentials.create(
		#   friendly_name: 'MyFCMCredential',
		#   type: 'fcm',
		#   secret: identity
		# )

		# puts credential

		notification = service.notifications.create(
		  identity: 'test2',
		  body: 'Hello World! Test test'
		)

		puts notification.sid
		# json_response(notification)
	end
end
