class TokensController < ApplicationController
	# before_filter :authenticate_coach!

	# GET for the token used for authentication
	def index
		if User.find_by(auth_token: params[:auth_token])
			@user = User.find_by(auth_token: params[:auth_token])
			json_response(user_id: @user.id)
		else
			json_response({message: "Token not found!"}, 404)
		end
	end

	# POST for authentication token used for Twilio
	def create
		# Required for any Twilio Access Token
		account_sid = ENV['account_sid']
		api_key = ENV['api_key']
		api_secret = ENV['api_secret']

		# Required for Chat
		service_sid = ENV['service_sid']
		identity = params[:name]

		# Create Chat grant for our token
		grant = Twilio::JWT::AccessToken::ChatGrant.new
		grant.service_sid = service_sid

		# Create an Access Token
		token = Twilio::JWT::AccessToken.new(
		  account_sid,
		  api_key,
		  api_secret,
		  [grant],
		  identity: identity
		)

		# Generate the token
		puts token.to_jwt
		
		# json_response(identity: identity, token: token.to_jwt)
		json_response(token: token.to_jwt)
	end
		
end
