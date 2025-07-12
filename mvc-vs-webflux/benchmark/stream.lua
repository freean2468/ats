
-- stream.lua
response = function(status, headers, body)
  -- This function is called for each response.
  -- By simply having this function, we are telling wrk to process the response.
  -- We don't need to do anything with the body itself for this use case.
end
