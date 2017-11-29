class PlayersNotificationsController < ApplicationController
		before_action :set_players_notification, only: [:show, :update, :destroy]

  # GET /notifications
  def index
		@notifications = []
		# if user_id is specified then we return all notifications that had a counter=3
		if params[:user_id] != nil
			@players_notifications = PlayersNotification.where("user_id = ? AND counter = 3", params[:user_id])
  		@players_notifications.each do |notification|
  			@notification = Notification.find(notification.notification_id)
  			@notifications.push(@notification)
  		end
  	else
      @notifications = PlayersNotification.all
    end
    json_response(@notifications)
  end

  # POST /notifications
  def create
    @players_notification = PlayersNotification.create!(players_notification_params)
    json_response(@players_notification, :created)
  end

  # GET /notifications/:id
  def show
    json_response(@players_notification)
  end

  # PUT /notifications/:id
  def update
    @players_notification.update(players_notification_params)
    head :no_content
  end

  # DELETE /notifications/:id
  def destroy
    @players_notification.destroy
    head :no_content
  end

  private

  def players_notification_params
    # whitelist params
    params.permit( :counter, :notification_id, :user_id, :received )
  end

  def set_players_notification
    @players_notification = PlayersNotification.find(params[:id])
  end

end
