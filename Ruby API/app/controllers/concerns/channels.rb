<<<<<<< HEAD
module Channels

	# method to create a chat channel for a player
	def createChannel(player_email)
		@client = Twilio::REST::Client.new(ENV['account_sid'], ENV['auth_token'])
		# Create the channel
		service = @client.chat.v2.services(ENV['service_sid'])
		player = User.find_by(email: player_email)
		channel = service.channels.create(unique_name: player.id)
		puts "Channel #{channel.sid} (\"#{channel.unique_name}\") created!"
		# coach_email = Coach.find(player.coach_id)[:email]
		# create members for channel
		# member = channel.members.create(identity: player_email)
		# member2 = channel.members.create(identity: coach_email)
		# puts member
		# puts member2
		# puts "New channel was created for "+player_email+" and "+coach_email
	end

	# method to delete a channel 
	def deleteChannel(id)
		@client = Twilio::REST::Client.new(ENV['account_sid'], ENV['auth_token'])

		# Delete channel
		service = @client.chat.v2.services(ENV['service_sid'])
		response = service.channels(id).delete
		if response
			puts "Channel was deleted"
		else
			puts "Channel could not be deleted"
		end
	end
=======
module Channels

	# method to create a chat channel for a player
	def createChannel(player_email)
		@client = Twilio::REST::Client.new(ENV['account_sid'], ENV['auth_token'])
		# Create the channel
		service = @client.chat.v2.services(ENV['service_sid'])
		player = User.find_by(email: player_email)
		channel = service.channels.create(unique_name: player.id)
		puts "Channel #{channel.sid} (\"#{channel.unique_name}\") created!"
		# coach_email = Coach.find(player.coach_id)[:email]
		# create members for channel
		# member = channel.members.create(identity: player_email)
		# member2 = channel.members.create(identity: coach_email)
		# puts member
		# puts member2
		# puts "New channel was created for "+player_email+" and "+coach_email
	end

	# method to delete a channel 
	def deleteChannel(id)
		@client = Twilio::REST::Client.new(ENV['account_sid'], ENV['auth_token'])

		# Delete channel
		service = @client.chat.v2.services(ENV['service_sid'])
		response = service.channels(id).delete
		if response
			puts "Channel was deleted"
		else
			puts "Channel could not be deleted"
		end
	end
>>>>>>> 56b6fe42c90710e40cc1403a5b27bd420244a2c0
end