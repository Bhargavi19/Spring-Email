INSERT INTO mail SELECT 1 as id, 'test body' as body, 'user@gmail.com' as from_address, 'test subject' as subject, 'user@gmail.com' as to_address, 'mail' as type from mail where(id=1) HAVING COUNT(*) = 0;

INSERT INTO mail SELECT 2 as id, 'test body' as body, 'user@gmail.com' as from_address, 'test subject' as subject, 'user@gmail.com' as to_address, 'draft' as type from mail where(id=2) HAVING COUNT(*) = 0;

INSERT INTO mail SELECT 3 as id, 'test body' as body, 'user@gmail.com' as from_address, 'test subject' as subject, 'user@gmail.com' as to_address, 'deleted' as type from mail where(id=3) HAVING COUNT(*) = 0;
