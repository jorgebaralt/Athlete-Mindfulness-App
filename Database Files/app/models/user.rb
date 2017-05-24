class User < ActiveRecord::Base

	has_secure_password
	validates_uniqueness_of :username, :case_sensitive => false
	validates_uniqueness_of :email, :case_sensitive => false

	has_many :players
	has_many :coaches
end
