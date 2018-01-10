<<<<<<< HEAD
module Accessible
  extend ActiveSupport::Concern
  # used for authorization
  # included do
  #   before_action :check_user
  # end

  # protected
  # def check_user
  #   if current_coach
  #     # if you have rails_admin. You can redirect anywhere really
  #     redirect_to('/coaches') && return
  #   elsif current_player
  #     # The authenticated root path can be defined in your routes.rb in: devise_scope :user do...
  #     redirect_to('/players') && return
  #   end
  # end
=======
module Accessible
  extend ActiveSupport::Concern
  # used for authorization
  # included do
  #   before_action :check_user
  # end

  # protected
  # def check_user
  #   if current_coach
  #     # if you have rails_admin. You can redirect anywhere really
  #     redirect_to('/coaches') && return
  #   elsif current_player
  #     # The authenticated root path can be defined in your routes.rb in: devise_scope :user do...
  #     redirect_to('/players') && return
  #   end
  # end
>>>>>>> 56b6fe42c90710e40cc1403a5b27bd420244a2c0
end