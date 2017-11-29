class Coach < ApplicationRecord
  # Include default devise modules. Others available are:
  # :confirmable, :lockable, :timeoutable and :omniauthable

  devise :database_authenticatable, :registerable,
      :recoverable, :rememberable, :validatable,
      :authentication_keys => [:login]

  attr_accessor :login

	# model association 
	has_many :players
	has_many :questions
	# validations
end
