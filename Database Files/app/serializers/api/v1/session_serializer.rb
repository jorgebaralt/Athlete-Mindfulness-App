class Api::V1::SessionSerializer < Api::V1::BaseSerializer
	attributes :id, :first_name, :last_name, :email, :username, :admin, :token

	def token
		object.authentication_token
	end
end
