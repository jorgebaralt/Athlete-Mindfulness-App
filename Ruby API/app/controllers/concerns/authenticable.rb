module Authenticable

	def current_user
		@current_user ||= user.find_by(auth_token: :auth_token)
	end

	def authenticate_user_with_token!
			render json: { error: "user Not authenticated" }, status: :unauthorized unless user_signed_in?
	end

	def user_signed_in?
		current_user.present?
	end

end