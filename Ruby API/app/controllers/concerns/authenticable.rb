<<<<<<< HEAD
module Authenticable

	# used for authentication
	def current_user
		@current_user ||= user.find_by(auth_token: :auth_token)
	end

	def authenticate_user_with_token!
			render json: { error: "user Not authenticated" }, status: :unauthorized unless user_signed_in?
	end

	def user_signed_in?
		current_user.present?
	end

=======
module Authenticable

	# used for authentication
	def current_user
		@current_user ||= user.find_by(auth_token: :auth_token)
	end

	def authenticate_user_with_token!
			render json: { error: "user Not authenticated" }, status: :unauthorized unless user_signed_in?
	end

	def user_signed_in?
		current_user.present?
	end

>>>>>>> 56b6fe42c90710e40cc1403a5b27bd420244a2c0
end