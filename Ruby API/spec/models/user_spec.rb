require 'rails_helper'

RSpec.describe User, type: :model do
  	#Association test
	it { should have_many(:answers)}
	it { should have_many(:questions)}
	it { should have_many(:notifications)}
	#Validation tests
	it{should validate_presence_of(:first_name)}
	it{should validate_presence_of(:last_name)}
	it{should validate_presence_of(:email)}
	it{should validate_presence_of(:encrypted_password)}
end
