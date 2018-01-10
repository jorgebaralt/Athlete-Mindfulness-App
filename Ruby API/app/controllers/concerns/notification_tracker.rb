<<<<<<< HEAD
module NotificationTracker

	# module used for the points system and to sense patterns to push notifications to the player

	# resets the counter using the question and user 
	def resetCounter(question_id, user_id)
		# puts (Notification.find_by(question_id: question_id))
		@notification = Notification.find_by(question_id: question_id)
		# puts @notification
		# if we find the player notification
		# puts user_id
		if PlayersNotification.find_by(notification_id: @notification[:id], user_id: user_id) != nil
			puts "reset counter"
			@player_notification = PlayersNotification.find_by(notification_id: @notification[:id], user_id: user_id)
			if @player_notification[:counter] < 3
				PlayersNotification.where('notification_id = ? AND user_id = ?', @notification[:id], @player_notification[:user_id]).update_all(counter: 0)
			else
				puts "we don't need to reset because we already sent the notification"
			end
		else
			puts "Player Notification not created so we don't need to reset counter"
		end

	end

	# method to add points to the player
	def addPointsToPlayer(user_id, player_points)
		@player = User.find(user_id)
		new_points = @player.points + player_points.to_i
		@player.update(points: new_points)
	end

	# method to increment the counter in the players_notification model
	# used to send push notifications if the counter reaches 3
	def incrementNotification(question_id, user_id)
    @notification = Notification.find_by(question_id: question_id)
    # puts @notification[:id]
    # puts @notification[:message]
    # if we find the player_notification and we have not already sent it then we increment the counter
    if @player_notification = PlayersNotification.find_by(notification_id: @notification[:id], user_id: user_id)
      # @player_notification[:counter] = @player_notification[:counter]+1
      puts @player_notification[:counter]
    	@new_counter = @player_notification[:counter] + 1
    	if @player_notification[:received] == 1 || @new_counter > 3
    		puts "already sent notification"
    		# json_response(@answer, :created)
      elsif @new_counter == 3
    		puts "send notification"

    		client = Twilio::REST::Client.new(ENV['account_sid'], ENV['auth_token'])
				service = client.notify.v1.services(ENV['notify_service_sid'])
				identity = user_id.to_s

				notification = service.notifications.create(
				  identity: identity,
				  body: "New Resources"
				)
				puts notification
				# puts notification.sid
				# json_response(notification: notification)
	      PlayersNotification.where('notification_id = ? AND user_id = ?', @notification[:id], @player_notification[:user_id]).update_all(counter: @new_counter, received: true)
    		# json_response(@notification)
    	else
	      puts @player_notification[:counter] + 1
	      PlayersNotification.where('notification_id = ? AND user_id = ?', @notification[:id], @player_notification[:user_id]).update_all(counter: @new_counter )
    		# json_response(@player_notification)
    		puts "added to the counter"
    	end
      # json_response(@player_notification, :no_content)
    else
    	player_notification_params = {
					:notification_id => @notification[:id],
					:user_id => user_id,
					:counter => 1,
					:received => false
				}
			puts player_notification_params
			@player_notification = PlayersNotification.create!(player_notification_params)
			# json_response(@player_notification, :created)
			puts @player_notification[:user_id]
      puts "we didn't find it"
    end
	end
=======
module NotificationTracker

	# module used for the points system and to sense patterns to push notifications to the player

	# resets the counter using the question and user 
	def resetCounter(question_id, user_id)
		# puts (Notification.find_by(question_id: question_id))
		@notification = Notification.find_by(question_id: question_id)
		# puts @notification
		# if we find the player notification
		# puts user_id
		if PlayersNotification.find_by(notification_id: @notification[:id], user_id: user_id) != nil
			puts "reset counter"
			@player_notification = PlayersNotification.find_by(notification_id: @notification[:id], user_id: user_id)
			if @player_notification[:counter] < 3
				PlayersNotification.where('notification_id = ? AND user_id = ?', @notification[:id], @player_notification[:user_id]).update_all(counter: 0)
			else
				puts "we don't need to reset because we already sent the notification"
			end
		else
			puts "Player Notification not created so we don't need to reset counter"
		end

	end

	# method to add points to the player
	def addPointsToPlayer(user_id, player_points)
		@player = User.find(user_id)
		new_points = @player.points + player_points.to_i
		@player.update(points: new_points)
	end

	# method to increment the counter in the players_notification model
	# used to send push notifications if the counter reaches 3
	def incrementNotification(question_id, user_id)
    @notification = Notification.find_by(question_id: question_id)
    # puts @notification[:id]
    # puts @notification[:message]
    # if we find the player_notification and we have not already sent it then we increment the counter
    if @player_notification = PlayersNotification.find_by(notification_id: @notification[:id], user_id: user_id)
      # @player_notification[:counter] = @player_notification[:counter]+1
      puts @player_notification[:counter]
    	@new_counter = @player_notification[:counter] + 1
    	if @player_notification[:received] == 1 || @new_counter > 3
    		puts "already sent notification"
    		# json_response(@answer, :created)
      elsif @new_counter == 3
    		puts "send notification"

    		client = Twilio::REST::Client.new(ENV['account_sid'], ENV['auth_token'])
				service = client.notify.v1.services(ENV['notify_service_sid'])
				identity = user_id.to_s

				notification = service.notifications.create(
				  identity: identity,
				  body: "New Resources"
				)
				puts notification
				# puts notification.sid
				# json_response(notification: notification)
	      PlayersNotification.where('notification_id = ? AND user_id = ?', @notification[:id], @player_notification[:user_id]).update_all(counter: @new_counter, received: true)
    		# json_response(@notification)
    	else
	      puts @player_notification[:counter] + 1
	      PlayersNotification.where('notification_id = ? AND user_id = ?', @notification[:id], @player_notification[:user_id]).update_all(counter: @new_counter )
    		# json_response(@player_notification)
    		puts "added to the counter"
    	end
      # json_response(@player_notification, :no_content)
    else
    	player_notification_params = {
					:notification_id => @notification[:id],
					:user_id => user_id,
					:counter => 1,
					:received => false
				}
			puts player_notification_params
			@player_notification = PlayersNotification.create!(player_notification_params)
			# json_response(@player_notification, :created)
			puts @player_notification[:user_id]
      puts "we didn't find it"
    end
	end
>>>>>>> 56b6fe42c90710e40cc1403a5b27bd420244a2c0
end