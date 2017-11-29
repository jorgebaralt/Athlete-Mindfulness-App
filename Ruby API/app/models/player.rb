class Player < ApplicationRecord
  # Include default devise modules. Others available are:
  # :confirmable, :lockable, :timeoutable and :omniauthable

  devise :database_authenticatable, :registerable,
      :recoverable, :rememberable, :validatable,
      :authentication_keys => [:login]
	
	attr_accessor :login

	# model association
	belongs_to :coach
	has_many :answers
	has_many :questions
	# validations
	validates_presence_of :gender
	validates_presence_of :points
	validates_presence_of :age
	validates_presence_of :age_range
	
end
