class DemoController < ApplicationController
	
	before_action :require_user, only: [:index, :show]
  
  layout false

  def index
    render('index')
  end
end
